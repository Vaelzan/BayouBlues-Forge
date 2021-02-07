package com.teamaurora.bayou_blues.common.world.gen.treedecorator;

import com.mojang.serialization.Codec;
import com.teamaurora.bayou_blues.common.block.DoubleCypressKneeBlock;
import com.teamaurora.bayou_blues.core.registry.BayouBluesBlocks;
import com.teamaurora.bayou_blues.core.registry.BayouBluesFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class CypressKneesTreeDecorator extends TreeDecorator {
    public static final Codec<CypressKneesTreeDecorator> CODEC;
    public static final CypressKneesTreeDecorator DECORATOR = new CypressKneesTreeDecorator();

    @Override
    protected TreeDecoratorType<?> func_230380_a_() {
        return BayouBluesFeatures.CYPRESS_KNEES.get();
    }

    static {
        CODEC = Codec.unit(() -> DECORATOR);
    }

    @Override
    public void func_225576_a_(ISeedReader world, Random rand, List<BlockPos> logs, List<BlockPos> leaves, Set<BlockPos> updatedBlocks, MutableBoundingBox bounds) {
        int minY = world.getHeight();
        for (BlockPos pos : logs) {
            if (pos.getY() < minY) minY = pos.getY();
        }
        for (BlockPos pos : logs) {
            if (pos.getY() == minY) {
                for (int x = -2; x <= 2; x++) {
                    for (int y = -2; y <= 2; y++) {
                        for (int z = -2; z <= 2; z++) {
                            BlockPos newPos = pos.add(x, y, z);
                            if (pos.withinDistance(newPos, 2.0D)) {
                                if (world.getBlockState(newPos.down()).getBlock() == Blocks.GRASS_BLOCK && world.getBlockState(newPos).isAir()) {
                                    if (rand.nextInt(12) == 0) {
                                        if (rand.nextInt(3) == 0 && world.getBlockState(newPos.up()).isAir()) {
                                            ((DoubleCypressKneeBlock) BayouBluesBlocks.LARGE_CYPRESS_KNEE.get()).placeAt(world, newPos, 3);
                                        } else {
                                            world.setBlockState(newPos, BayouBluesBlocks.CYPRESS_KNEE.get().getDefaultState(), 3);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}