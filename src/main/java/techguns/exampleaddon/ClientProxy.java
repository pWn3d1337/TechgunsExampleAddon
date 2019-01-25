package techguns.exampleaddon;

import techguns.client.models.guns.ModelPistol;
import techguns.client.render.ItemRenderHack;
import techguns.client.render.fx.ScreenEffect;
import techguns.client.render.item.GunAnimation;
import techguns.client.render.item.RenderGunBase;
import techguns.client.render.item.RenderItemBase;

public class ClientProxy extends CommonProxy {

	@Override
	public void init() {

		/**
		 * Register the 3D item Renderer for the model. Refer to Techguns.client.ClientProxy for more examples
		 */
		ItemRenderHack.registerItemRenderer(TechgunsExampleAddon.laserpistol, //The weapon to register the renderer
				new RenderGunBase(new ModelPistol(),2) //The renderer for the item, and the model passed as argument, 2 means the model has 2 parts, which is used for animation the slide
				.setBaseTranslation(RenderItemBase.SCALE*0.5f, -0.3f, -0.4f) //define the base translation of the gun model
				.setBaseScale(1.2f).setGUIScale(0.9f) //define the base scale and the gui scale (inventory)
				.setMuzzleFx(ScreenEffect.muzzleFlashBlaster, 0.03f, 0.2f, -0.5f, 0.55f,-0.03f) //define the muzzleflash and positioning, x, y, z, scale, x_l (x when holding the gun in left hand, usually -x when the model is centered)
				.setTransformTranslations(new float[][]{ //define positions for specific rendertypes
					{0,0.09f,-0.02f}, //First Person - holding in 1st person
					{0.0f,-0.03f,0.0f}, //Third Person - holding in 3rd person
					{0.02f,-0.08f,0}, //GUI - inventory
					{0.02f,-0.08f,0}, //Ground - lying on ground
					{0,0,0f} //frame - item frame
				}).setRecoilAnim(GunAnimation.genericRecoil, 0.025f, 12.0f) //define recoil animation, see techguns code for examples
				.setMuzzleFlashJitter(0.01f, 0.01f, 5.0f, 0.05f) //randomness to muzzle flash
				.setMuzzleFXPos3P(0.07f, -0.26f)); //3rd person position for muzzle flash, y (height) and z (forward-backward)
	}

}
