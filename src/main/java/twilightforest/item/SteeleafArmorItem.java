package twilightforest.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import twilightforest.TwilightForestMod;

public class SteeleafArmorItem extends ArmorItem {

	public SteeleafArmorItem(ArmorMaterial material, Type type, Properties properties) {
		super(material, type, properties);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String layer) {
		if (slot == EquipmentSlot.LEGS) {
			return TwilightForestMod.ARMOR_DIR + "steeleaf_2.png";
		} else {
			return TwilightForestMod.ARMOR_DIR + "steeleaf_1.png";
		}
	}
}