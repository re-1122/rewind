package com.koyomiji.rewind.config;

import com.koyomiji.rewind.ReWind;
import com.koyomiji.rewind.debug.DebugOverlayStyle;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/acf537dad272a4a7148d8e2f124e1bdf2226f2a4/src/main/java/choonster/testmod3/config/ModConfig.java

@Config(modid = ReWind.MODID)
@Config.LangKey("rewind.config.title")
public class ReWindConfig {
  @Config.Comment(
      "[-1.10.2] This affects the dates shown in the world selection screen.")
  @Config.RequiresMcRestart
  public static boolean preventSettingDefaultLocale = true;

  @Config.Comment("[-1.8.9] Always show the enchantment level in the tooltip.")
  public static boolean alwaysShowEnchantmentLevel = true;

  @Config.Comment("[-1.11.2] Hide Java Edition from the main menu.")
  public static boolean hideJavaEdition = true;

  @Config.Comment("[-1.8.9] Prevent hoes from taking damage when attacking.")
  public static boolean preventHoeDamageWhenAttack = true;

  @Config.Comment("[-1.11] Allow Infinity and Mending on bows.")
  public static boolean allowInfinityMendingBow = true;

  @Config.Comment("[-1.11] Remove sweeping edge enchantment.")
  public static boolean removeSweepingEdge = true;

  @Config.Comment("[-1.10.2] Remove cursed enchantments.")
  public static boolean removeCursedEnchantments = true;

  @Config.Comment(
      "[-1.8.9] Prevent shears from taking damage when breaking regular blocks.")
  public static boolean preventShearsDamageForRegularBlocks = true;

  @Config.Comment("[-1.8.9] Allow silk touch on shears.")
  public static boolean allowSilkTouchOnShears = true;

  public static DebugOverlayStyle debugOverlayStyle = DebugOverlayStyle.DEFAULT;

  @Config.Comment("[-1.8.9] Hide the death cause on the game over screen.")
  public static boolean hideDeathCauseOnGameOverScreen = true;

  @Config.Comment("[-1.11.2] Prevent beds from bouncing the player.")
  public static boolean preventBedBounce = true;

  @Config.Comment("[-1.9.4] Turn off auto-jump when booting the game.")
  public static boolean turnOffAutoJumpAutomatically = true;

  @Config.Comment("[-1.8.9] Show the crosshair in third person view.")
  public static boolean showCrosshairInThirdPersonView = true;

  @Config.Comment("[-1.10.2] Show the overlay message in chat.")
  public static boolean showOverlayMessageInChat = true;

  @Config.Comment(
      "[-1.10.2] Sneaking prevents the player from falling off 1 block instead of 0.6.")
  public static boolean sneakingStepHeight1 = true;

  @Config.Comment("[-1.8.9] Set momentum threshold to 0.005 instead of 0.003.")
  public static boolean momentumThreshold0_005 = true;

  @Config.Comment("[-1.11.2] Rotate the player model when moving backwards.")
  public static boolean rotatePlayerModelWhenMovingBackwards = true;

  @Config.Comment(
      "[-1.8.9] Disable faster regeneration when the player's food level is full.")
  public static boolean disableFasterRegeneration = true;

  @Config.Comment("[-1.9.4] Do not render falling block particles.")
  public static boolean preventFallingBlockParticle = true;

  @Config.Comment(
      "[-1.10.2] Less brighter durability bar color and durability bar background.")
  public static boolean lessBrighterDurabilityBar = true;

  @Config.Comment("[-1.11.2] Disable recipe book.")
  public static boolean disableRecipeBook = true;

  @Config.Comment("1.8-esque inventory.")
  @Config.RequiresMcRestart
  public static boolean rearrangeInventory = true;

  @Config.Comment("[-1.8.9] Prevent mobs from pushing the player.")
  public static boolean preventMobPushingPlayer = true;

  @Config.Comment("[-1.8.9] Disable item cooldown.")
  public static boolean disableItemCooldown = true;

  @Config.Comment("[-1.10.2] Disable entity cramming by default.")
  @Config.RequiresMcRestart
  public static boolean disableEntityCrammingByDefault = true;

  @Config.Comment("[-1.10.2] Prevent spawning nitwit villagers.")
  public static boolean preventSpawningNitwits = true;

  @Config.Comment(
      "[-1.11.2] Shift inventory window to the left when the player has potion effects.")
  public static boolean shiftInventoryWhenHavingEffects = true;

  @Config.Comment("[-1.8.9] Hide active potion effect overlay.")
  public static boolean hideActiveEffects = true;

  @Config.Comment("[-1.8.9] Add Super Secret Settings to the options menu.")
  public static boolean addSuperSecretSettings = true;

  @Config.Comment("[-1.8.9] Combat mechanism from 1.8-era.")
  @Config.RequiresMcRestart
  public static boolean oldCombat = true;

  @Config.Comment("[-1.8.9] Enable interaction when rowing a boat.")
  public static boolean enableInteractionWhenRowingBoat = true;

  @Config.Comment("[-1.8.9] Disable iron golem's knockback resistance.")
  public static boolean disableIronGolemKnockbackResistance = true;

  @Config.Comment("[-1.8.9] Disable shield recipe.")
  public static boolean disableShieldRecipe = true;

  @Config.Comment("[-1.8.9] Prevent skeleton horse traps.")
  public static boolean preventSkeletonHorseTrap = true;

  @Config.Comment("[-1.8.9] Old damage calculation.")
  public static boolean oldDamageCalculation = true;

  @Config.Comment("[-1.8.9] Old weapon tooltip.")
  public static boolean oldWeaponTooltip = true;

  @Config.Comment("1.11.2-like bed icons.")
  @Config.RequiresMcRestart
  public static boolean traditionalBedIcon = true;

  @Config.Comment("[-1.8.9] Skeletons behave like 1.8-era.")
  public static boolean traditionalSkeletonBehavior = true;

  @Config.Comment("[-1.8.9] Zombies behave like 1.8-era.")
  public static boolean traditionalZombieBehavior = true;

  @Config.Comment("[-1.11.2] Replace advancements with achievements.")
  public static boolean enableAchievement = true;

  @Config.Comment("[-1.8.9] End spikes generation from 1.8-era.")
  public static boolean classicEndSpikes = true;

  @Config.Comment("[-1.8.9] Ender dragon behavior from 1.8-era.")
  public static boolean traditionalEnderDragonBehavior = true;

  @Config.Comment("[-1.9.4] Prevent spawning endermen in the nether.")
  public static boolean preventEndermenInNether = true;

  @Config.Comment(
      "[-1.8.9] By enabling this, a bow no longer goes down when you are drawing it.")
  public static boolean traditionalItemUseAnimation = true;

  @Config.Comment("[-1.8.9] Zombie villagers will look like 1.8-era.")
  public static boolean traditionalZombieVillager = true;

  @Config.Comment("[-1.9.4] Prevent generating zombie villages.")
  public static boolean preventZombieVillage = true;

  @Config.
  Comment("[-1.8.8] Remove \"Realms Notifications\" button in game setting.")
  public static boolean removeRealmsNotificationsOption = true;

  @Config.Comment(
      "[-1.7.10] Some blocks such as dirt, grass, etc. will not rotate their texture.")
  @Config.RequiresMcRestart
  public static boolean noBlockTextureRotation = true;

  @Config.Comment("[-1.7.10] A bit darker grass color.")
  @Config.RequiresMcRestart
  public static boolean traditionalGrassTexture = true;

  @Config.Comment("[-1.8.9] A golden heart absorption icon.")
  @Config.RequiresMcRestart
  public static boolean traditionalAbsorptionIcon = true;

  @Config.Comment(
      "[-1.8.9] Some items such as a bow, a fishing rod, etc. will not animate in the hot bar.")
  public static boolean disableItemOverridesInHotBar = true;

  @Config.
  Comment("[-1.8.9] Harvesting cobweb requires a shear with silk touch.")
  public static boolean cobwebHarvestableOnlyWithSilkTouch = true;

  @Config.Comment("[-1.8.9] Hunger exhaustion value from 1.8-era.")
  public static boolean traditionalExhaustionValue = true;

  @Config.Comment("[-1.11.2] Flip bed texture horizontally.")
  public static boolean traditionalBedSurfaceTexture = true;

  @Config.Comment(
      "[-1.8.9] Strength effect increases the player's damage by 130%, weakness effect decreases it by 50%, and the duration of potion effects is changed.")
  public static boolean traditionalPotionEffects = true;

  @Config.Comment(
          "[-1.9.4] Prevent villages from being generated across biome boundaries.")
  public static boolean biomeBoundedVillages = true;

  @Config.Comment(
          "[-1.9.4] Prevent generating villages in the taiga biome.")
  public static boolean noTaigaVillage = true;

  public static Sounds sounds = new Sounds();

  public static class Sounds {
    @Config.Comment("[-1.8.9]") public boolean traditionalChestSound = true;
    @Config.Comment("[-1.8.9]")
    public boolean traditionalZombieVillagerSound = true;
    @Config.Comment("[-1.8.9]") public boolean noKillerBunnyAttackSound = true;
    @Config.Comment("[-1.8.9]") public boolean noItemEquipSound = true;
    @Config.Comment("[-1.8.9]") public boolean traditionalHoeSound = true;
    @Config.Comment("[-1.8.9]") public boolean noSquidSound = true;
    @Config.Comment("[-1.8.9]") public boolean noWitchSound = true;
    @Config.Comment("[-1.8.9]")
    public boolean traditionalWitherSkeletonSound = true;
    @Config.Comment("[-1.8.9]") public boolean traditionalSwitchSound = true;
    @Config.Comment("[-1.8.9]") public boolean traditionalDoorSound = true;
    @Config.Comment("[-1.8.9]") public boolean noFurnaceSound = true;
    @Config.Comment("[-1.8.9]") public boolean noLilyPadSound = true;
    @Config.Comment("[-1.8.9]") public boolean noGlassBottleSound = true;
    @Config.Comment("[-1.8.9]") public boolean noBucketSound = true;
    @Config.Comment("[-1.8.9]") public boolean noCowMilkSound = true;
    @Config.Comment("[-1.8.9]") public boolean noSnowmanSound = true;
    @Config.Comment("[-1.8.9]") public boolean noItemFrameSound = true;
    @Config.Comment("[-1.8.9]") public boolean noLeashKnotSound = true;
    @Config.Comment("[-1.8.9]") public boolean noPaintingSound = true;
    @Config.Comment("[-1.8.9]") public boolean traditionalGlassSound = true;
  }

  @Mod.EventBusSubscriber(modid = ReWind.MODID)
  private static class EventHandler {
    @SubscribeEvent
    public static void
    onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
      if (event.getModID().equals(ReWind.MODID)) {
        ConfigManager.sync(ReWind.MODID, Config.Type.INSTANCE);
      }
    }
  }
}