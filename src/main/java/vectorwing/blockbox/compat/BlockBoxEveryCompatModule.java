package vectorwing.blockbox.compat;

import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.modules.EveryCompatModule;
import net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodTypes;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import vectorwing.blockbox.common.block.PalisadeBlock;
import vectorwing.blockbox.common.block.SeatBlock;
import vectorwing.blockbox.common.registry.ModBlocks;
import vectorwing.blockbox.common.registry.ModCreativeTabs;

import java.util.function.Supplier;

import static net.mehvahdjukaar.moonlight.api.set.wood.VanillaWoodChildKeys.*;

public class BlockBoxEveryCompatModule extends EveryCompatModule {

    public final SimpleEntrySet<WoodType, Block> seat;

    public final SimpleEntrySet<WoodType, Block> palisade;
    public final SimpleEntrySet<WoodType, Block> spikedPalisade;
    public final SimpleEntrySet<WoodType, Block> strippedPalisade;
    public final SimpleEntrySet<WoodType, Block> strippedSpikedPalisade;


    public BlockBoxEveryCompatModule(String modId) {
        super(modId, "bb");
        ResourceLocation creativeTabRL = ModCreativeTabs.TAB_BLOCK_BOX.getId();

        this.seat = SimpleEntrySet.builder(WoodType.class, "seat",
                        getModBlock("oak_seat"), () -> VanillaWoodTypes.OAK,
                        w -> new SeatBlock(BlockBehaviour.Properties.ofFullCopy(w.planks)))
                .requiresChildren(PLANKS) //REASON: recipes
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .addTag(modRes("wooden_seats"), Registries.BLOCK, Registries.ITEM)
                .setTab(getTab(creativeTabRL))
                .addRecipe(modRes("oak_seat"))
                .copyParentDrop()
                .addTexture(modRes("block/oak_seat_side"))
                .addTexture(modRes("block/oak_seat_top"))
                .build();
        this.addEntry(seat);

        this.palisade = SimpleEntrySet.builder(WoodType.class, "palisade",
                        getModBlock("oak_palisade"), () -> VanillaWoodTypes.OAK,
                        w -> w.canBurn() ?
                                ModBlocks.netherPalisade(getPalisadeForm(w, PalisadeForm.SPIKED), getPalisadeForm(w, PalisadeForm.STRIPPED), w.getColor()) :
                                ModBlocks.palisade(getPalisadeForm(w, PalisadeForm.SPIKED), getPalisadeForm(w, PalisadeForm.STRIPPED), w.getColor())
                        )
                .requiresChildren(LOG) //REASON: recipes
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .addTag(modRes("palisades"), Registries.BLOCK, Registries.ITEM)
                .setTab(getTab(creativeTabRL))
                .copyParentDrop()
                .addRecipe(modRes("oak_palisade"))
                .addTexture(modRes("block/oak_palisade_core"))
                .addTexture(modRes("block/oak_palisade_side"))
                .addTexture(modRes("block/oak_palisade_top"))
                .build();
        this.addEntry(palisade);
        this.spikedPalisade = SimpleEntrySet.builder(WoodType.class, "spiked_palisade",
                        getModBlock("spiked_oak_palisade"), () -> VanillaWoodTypes.OAK,
                        w ->  ModBlocks.spikedPalisade(getPalisadeForm(w, PalisadeForm.STRIPPED_SPIKED), w.getColor()))
                .requiresChildren(LOG)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .addTag(modRes("spiked_palisades"), Registries.BLOCK, Registries.ITEM)
                .setTab(getTab(creativeTabRL))
                .copyParentDrop()
                .addRecipe(modRes("stripped_oak_palisade"))
                .addTexture(modRes("block/stripped_oak_palisade_core"))
                .addTexture(modRes("block/stripped_oak_palisade_side"))
                .addTexture(modRes("block/stripped_oak_palisade_top"))
                .build();
        this.addEntry(spikedPalisade);

        this.strippedPalisade = SimpleEntrySet.builder(WoodType.class, "stripped_palisade",
                        getModBlock("stripped_oak_palisade"), () -> VanillaWoodTypes.OAK,
                        w ->  ModBlocks.palisade(getPalisadeForm(w, PalisadeForm.STRIPPED_SPIKED), null, w.getColor()))
                .requiresChildren(LOG)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .addTag(modRes("palisades"), Registries.BLOCK, Registries.ITEM)
                .setTab(getTab(creativeTabRL))
                .copyParentDrop()
                .addRecipe(modRes("stripped_oak_palisade"))
                .addTexture(modRes("block/stripped_oak_palisade_core"))
                .addTexture(modRes("block/stripped_oak_palisade_side"))
                .addTexture(modRes("block/stripped_oak_palisade_top"))
                .build();
        this.addEntry(strippedPalisade);
        this.strippedSpikedPalisade = SimpleEntrySet.builder(WoodType.class, "stripped_spiked_palisade",
                        getModBlock("stripped_spiked_oak_palisade"), () -> VanillaWoodTypes.OAK,
                        w ->  ModBlocks.spikedPalisade(getPalisadeForm(w, PalisadeForm.STRIPPED_SPIKED), w.getColor()))
                .requiresChildren(LOG)
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registries.BLOCK)
                .addTag(modRes("spiked_palisades"), Registries.BLOCK, Registries.ITEM)
                .setTab(getTab(creativeTabRL))
                .copyParentDrop()
                .addRecipe(modRes("stripped_oak_palisade"))
                .addTexture(modRes("block/stripped_oak_palisade_core"))
                .addTexture(modRes("block/stripped_oak_palisade_side"))
                .addTexture(modRes("block/stripped_oak_palisade_top"))
                .build();
        this.addEntry(strippedSpikedPalisade);

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
