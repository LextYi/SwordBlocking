package eu.midnightdust.swordblocking.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(at = @At(value = "HEAD"), method = "use", cancellable = true)
    public void parry$allowSwordUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if(user.getStackInHand(hand).getItem() instanceof SwordItem && user.getStackInHand(Hand.OFF_HAND).getItem() instanceof ShieldItem) {
            user.setCurrentHand(hand);
            user.setSprinting(false);
            cir.setReturnValue(TypedActionResult.pass(user.getStackInHand(hand)));
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "getMaxUseTime", cancellable = true)
    public void parry$applySwordUseTime(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if(stack.getItem() instanceof SwordItem) {
            cir.setReturnValue(72000);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "getUseAction", cancellable = true)
    public void parry$returnBlockUseAction(ItemStack stack, CallbackInfoReturnable<UseAction> cir) {
        if(stack.getItem() instanceof SwordItem) {
            cir.setReturnValue(UseAction.BLOCK);
        }
    }
}
