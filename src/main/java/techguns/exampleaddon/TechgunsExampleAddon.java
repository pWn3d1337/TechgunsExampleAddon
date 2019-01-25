package techguns.exampleaddon;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import org.apache.logging.log4j.Logger;

import techguns.TGItems;
import techguns.TGSounds;
import techguns.TGuns;
import techguns.api.guns.GunHandType;
import techguns.items.armors.GenericArmor;
import techguns.items.armors.TGArmorMaterial;
import techguns.items.guns.GenericGun;

/**
 * Add Techguns as dependency
 */
@EventBusSubscriber
@Mod(modid = TechgunsExampleAddon.MODID, name = TechgunsExampleAddon.NAME, version = TechgunsExampleAddon.VERSION, dependencies=TechgunsExampleAddon.DEPENDENCIES)
public class TechgunsExampleAddon
{
    public static final String MODID = "techgunsexampleaddon";
    public static final String NAME = "Techguns Example addon";
    public static final String VERSION = "1.0";
    public static final String DEPENDENCIES = "required-after:techguns@2.0.1.2,)"; 


    @Mod.Instance
    public static TechgunsExampleAddon instance;
    
    /**
     * Forge proxy system. Use the clientside proxy to init clientside stuff, like gun models
     */
    @SidedProxy(clientSide = "techguns.exampleaddon.ClientProxy", serverSide = "techguns.exampleaddon.CommonProxy")
    public static CommonProxy proxy;

    
    /**
     * Define static variables for guns
     */
    public static GenericGun laserpistol;
    
    /**
     * Define static variables for armor
     */
    public static GenericArmor carbon_helmet;
	public static GenericArmor carbon_chestplate;
	public static GenericArmor carbon_leggings;
	public static GenericArmor carbon_boots;
    
	//Define the armor material
	public static TGArmorMaterial MATERIAL_CARBON;
	
    /**add a new creative tab*/
    public static CreativeTabs tabTechgunsExampleAddon = new CreativeTabs(TechgunsExampleAddon.MODID) {
	    @Override
	    @SideOnly(Side.CLIENT)
	    public ItemStack getTabIconItem() {
	        return new ItemStack(laserpistol,1);
	    }

		@Override
		public String getTranslatedTabLabel() {
			return TechgunsExampleAddon.MODID+"."+super.getTranslatedTabLabel();
		}
	};
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	/**
    	 * 
    	 * 
    	 * Create a new Gun
    	 * 
    	 * 
    	 * 
    	 */
       laserpistol = new GenericGun(false, //defines if a gun is added to the gunlist, which it should not when called from another mod than techguns directly
    		   "laserpistol", //the item name
    		   TGuns.LASERGUN_PROJECTILES, //A projectile Definition, you can use existing ones from TGuns or create new ones, look at Techguns source how it works
    		   true, // semi auto?
    		   2, //minimum delay between 2 shots in ticks
    		   20, //Amount of shots per magazine
    		   20, //time in ticks to reload the guns
    		   20f, //base damage
    		   TGSounds.LASERGUN_FIRE, //Sound event that is played when firing
    		   TGSounds.LASERGUN_RELOAD, //sound event for reloading, duration of sound should match reloadtime (1seconds = 20ticks)
    		   80, //named TTL for time to live, but actually is max range now. TTL of a projectile is scaled with projectile speed
    		   0.0f); //accuracy, 0.0f = exactly accurate
       /*additional parameters that can be set for guns, these functions return the gun and can be chained too*/  
       laserpistol.setAIStats(40f, 20, 3, 5); //Set AI behaviour (for turrent & mobs): Attack for up to range of 40, with bursts of 3. Delay of 5 ticks withing burst, delay of 20 between bursts
       laserpistol.setBulletSpeed(100f); //how fast a bullet flies, in case of a laser projectile this is a bit different and actually the range as it instant traces that distance
       laserpistol.setHandType(GunHandType.ONE_HANDED); //set to 1-handed as it's a pistol
       laserpistol.setDamageDrop(20f, 40f, 10f); //set damage drop, max damage until distance 20, interpolate between 20 and 40. Do 10 dmg for distance 40 and onwards.
       laserpistol.setTexture("textures/guns/pistol3"); //set the texture for the gun, a String will assume the texture is from techguns,
      	//for other modid pass a ResourceLocation. the .png file extension is automatically added, The function with ResourceLocation is added in 2.0.1.3, so check if you have the newest version
      // there is also the setTextures() method which takes a number as parameter, this is to define that the gun has multiple camos 
       
       //set the creative tab, otherwise the gun will land in the techguns tab (If you want that, don't set the tab)
       laserpistol.setCreativeTab(tabTechgunsExampleAddon);
       
       //set the unlocalized name to something different, otherwise it would be item.techguns.laserpistol.name, this would not be a problem unless addons want to add a gun with the same name, then the string will conflict, "item." and ".name" is added by the game
       laserpistol.setUnlocalizedName("techgunsexampleaddon.laserpistol");
       
       
       
       
       
       /**
        * 
        * 
        * Create a new Armor
        * 
        * 
        * 
        * 
        */
       //armor values are for the total suit, they are split to the part with defined factors 
       MATERIAL_CARBON = new TGArmorMaterial(TechgunsExampleAddon.MODID, //set the modid, this is required for vanilla type model texture path
    		   "carbon", 240, 0, 21.0f, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,2.0f) //set durability, enchantability, equipsound and toughness
    		   .setArmorFire(18.0f).setArmorExplosion(18.0f).setArmorEnergy(19.0f).setArmorIce(18.0f).setArmorLightning(18.0f).setArmorPoison(10.0f).setArmorRadiation(12.0f); //set damage type specific armor values

   		
       /**
        * Add the armors
        */
       carbon_helmet = (GenericArmor) new GenericArmor(TechgunsExampleAddon.MODID,  //use the constructor with Modid to the addon mod, this is needed for texture path and registry name
    		   "carbon_helmet", MATERIAL_CARBON, "carbon", EntityEquipmentSlot.HEAD) //name, MAterial, texture, slot; for GenericArmorMultiCamo the texture is an Array
    		   .setSpeedBoni(0.10f,0.f) //set a speed bonus
    		   .setRepairMats(TGItems.PLATE_CARBON, TGItems.CARBON_FIBERS, 0.5f, 2)// set repair mats, Material1, Material2, split factor, 2 in total
    		   .setKnockbackResistance(0.05f) //set a knockbackresistance
    		   .setCreativeTab(tabTechgunsExampleAddon); //setCreative tab does not return a GenericArmor, so it's called last and a cast is required
       
       carbon_chestplate = (GenericArmor) new GenericArmor(TechgunsExampleAddon.MODID, "carbon_chestplate", MATERIAL_CARBON, "carbon", EntityEquipmentSlot.CHEST).setSpeedBoni(0.10f,0.f).setRepairMats(TGItems.PLATE_CARBON, TGItems.CARBON_FIBERS, 0.5f, 4).setKnockbackResistance(0.20f).setCreativeTab(tabTechgunsExampleAddon);
       carbon_leggings = (GenericArmor) new GenericArmor(TechgunsExampleAddon.MODID, "carbon_leggings", MATERIAL_CARBON, "carbon", EntityEquipmentSlot.LEGS).setSpeedBoni(0.10f,0.f).setRepairMats(TGItems.PLATE_CARBON, TGItems.CARBON_FIBERS, 1.0f/3.0f, 3).setKnockbackResistance(0.10f).setCreativeTab(tabTechgunsExampleAddon);
       carbon_boots = (GenericArmor) new GenericArmor(TechgunsExampleAddon.MODID, "carbon_boots", MATERIAL_CARBON, "carbon", EntityEquipmentSlot.FEET).setSpeedBoni(0.10f,0.1f).setRepairMats(TGItems.PLATE_CARBON, TGItems.CARBON_FIBERS, 0.5f, 2).setKnockbackResistance(0.05f).setFallProtection(0.2f, 1.0f).setCreativeTab(tabTechgunsExampleAddon);
       
       //Armors don't need to be registered, Techguns adds all armors in a list an registers them. by setting the MODID the registry name is also updated
       
       
       //call preInit() of the proxy, forge handles client/serverside instance
       TechgunsExampleAddon.proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	TechgunsExampleAddon.proxy.init();
    }
}
