package techguns.exampleaddon;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {

    /**
     * Register the gun as item in the item registry event
     */
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event){
    	System.out.println("REGISTERING ITEMS");
		IForgeRegistry<Item> reg = event.getRegistry();
		
		reg.register(TechgunsExampleAddon.laserpistol);
    }
	
	public void preInit() {
		
	};
	
	public void init() {
		
	};
}
