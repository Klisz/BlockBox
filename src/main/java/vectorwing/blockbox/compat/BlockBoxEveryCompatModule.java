package vectorwing.blockbox.compat;

import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.modules.EveryCompatModule;
import net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodTypes;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import vectorwing.blockbox.common.block.PalisadeBlock;
import vectorwing.blockbox.common.registry.ModBlocks;
import vectorwing.blockbox.common.registry.ModCreativeTabs;

import java.util.function.Supplier;

import static net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodChildKeys.LOG;

//Modeled after WoodGood's BibliocraftLegacyModule:
//https://github.com/MehVahdJukaar/WoodGood/blob/1.21/neoforge/src/main/java/net/mehvahdjukaar/every_compat/modules/neoforge/bibliocraft/BibliocraftLegacyModule.java
public class BlockBoxEveryCompatModule extends EveryCompatModule {

    public final SimpleEntrySet<WoodType, Block> palisade;
    public final SimpleEntrySet<WoodType, Block> spikedPalisade;
    public final SimpleEntrySet<WoodType, Block> strippedPalisade;
    public final SimpleEntrySet<WoodType, Block> strippedSpikedPalisade;


    public BlockBoxEveryCompatModule(String modId) {
        super(modId, "bb");
        ResourceLocation creativeTabRL = ModCreativeTabs.TAB_BLOCK_BOX.getId();

        this.palisade = SimpleEntrySet.builder(WoodType.class, "palisade",
                        getModBlock("oak_palisade"), () -> VanillaWoodTypes.OAK,
                        w -> w.canBurn() ? ModBlocks.netherPalisade(getPalisadeForm(w, PalisadeForm.SPIKED), getPalisadeForm(w, PalisadeForm.STRIPPED), w.getColor()) : ModBlocks.palisade(getPalisadeForm(w, PalisadeForm.SPIKED), getPalisadeForm(w, PalisadeForm.STRIPPED), w.getColor()))
                .requiresChildren(LOG) //REASON: recipes
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .addTag(modRes("palisades"), Registries.BLOCK, Registries.ITEM)
                .setTab(getTab(creativeTabRL))
                .addRecipe(modRes("wood/oak/palisade"))
                .build();
        this.addEntry(palisade);
    }

    public Supplier<Block> getPalisadeForm(WoodType woodType, PalisadeForm palisadeForm){
        return () -> switch (palisadeForm){
            case NORMAL -> palisade.blocks.get(woodType);
            case SPIKED -> spikedPalisade.blocks.get(woodType);
            case STRIPPED -> strippedPalisade.blocks.get(woodType);
            case STRIPPED_SPIKED -> strippedSpikedPalisade.blocks.get(woodType);
        };
    }

    public enum PalisadeForm {
        NORMAL,
        SPIKED,
        STRIPPED,
        STRIPPED_SPIKED
    }


}
