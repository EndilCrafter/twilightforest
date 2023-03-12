package twilightforest.world.components.layer.vanillalegacy;

import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import twilightforest.world.components.layer.*;
import twilightforest.world.components.layer.vanillalegacy.area.Area;
import twilightforest.world.components.layer.vanillalegacy.area.LazyArea;
import twilightforest.world.components.layer.vanillalegacy.context.BigContext;
import twilightforest.world.components.layer.vanillalegacy.context.LazyAreaContext;

import java.util.function.LongFunction;
import java.util.function.Supplier;

public class Layer {
	public final LazyArea area;

	public Layer(Supplier<LazyArea> areaFactory) {
		this.area = areaFactory.get();
	}

	private static <T extends Area, C extends BigContext<T>> Supplier<T> makeLayers(LongFunction<C> seed, HolderGetter<Biome> registry) {
 		Supplier<T> biomes = GenLayerTFBiomes.INSTANCE.run(seed.apply(1L));
		biomes = GenLayerTFKeyBiomes.INSTANCE.run(seed.apply(1000L), biomes);
		biomes = GenLayerTFCompanionBiomes.INSTANCE.run(seed.apply(1000L), biomes);

		biomes = ZoomLayer.NORMAL.run(seed.apply(1000L), biomes);
		biomes = ZoomLayer.NORMAL.run(seed.apply(1001L), biomes);

		biomes = GenLayerTFBiomeStabilize.INSTANCE.run(seed.apply(700L), biomes);

		biomes = GenLayerTFThornBorder.INSTANCE.setup(registry).run(seed.apply(500L), biomes);

		biomes = ZoomLayer.NORMAL.run(seed.apply(1002), biomes);
		biomes = ZoomLayer.NORMAL.run(seed.apply(1003), biomes);
		biomes = ZoomLayer.NORMAL.run(seed.apply(1004), biomes);
		biomes = ZoomLayer.NORMAL.run(seed.apply(1005), biomes);

		Supplier<T> riverLayer = GenLayerTFStream.INSTANCE.run(seed.apply(1L), biomes);
		riverLayer = SmoothLayer.INSTANCE.run(seed.apply(7000L), riverLayer);
		biomes = GenLayerTFRiverMix.INSTANCE.setup().run(seed.apply(100L), biomes, riverLayer);

		return biomes;
	}

	public static Layer makeLayers(HolderGetter<Biome> registry) {
		return new Layer(makeLayers((salt) -> new LazyAreaContext(25, salt), registry));
	}

	public ResourceKey<Biome> get(int x, int z) {
		return this.area.get(x, z);
	}
}
