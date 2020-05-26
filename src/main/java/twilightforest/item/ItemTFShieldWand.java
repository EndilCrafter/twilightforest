package twilightforest.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.shield.IShieldCapability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemTFShieldWand extends ItemTF {

	protected ItemTFShieldWand(Properties props) {
		super(props);
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
		ItemStack stack = player.getHeldItem(hand);

		if (stack.getDamage() == stack.getMaxDamage()) {
			return new ActionResult<>(ActionResultType.FAIL, stack);
		}

		if (!world.isRemote) {
			player.getCapability(CapabilityList.SHIELDS).ifPresent(cap -> {
				cap.replenishShields();
				stack.damageItem(1, player, (user) -> user.sendBreakAnimation(hand));
			});
		}

		if (!player.isCreative())
			player.getCooldownTracker().setCooldown(this, 1200);

		return new ActionResult<>(ActionResultType.SUCCESS, stack);
	}

	@Override
	public float getXpRepairRatio(ItemStack stack) {
		return 0.1f;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
		super.addInformation(stack, world, tooltip, flags);
		tooltip.add(new TranslationTextComponent("twilightforest.scepter_charges", stack.getMaxDamage() - stack.getDamage()));
	}
}
