package core;

import util.Util;

import java.util.ArrayList;

public class BlockChainStarter {


    public static void main(String[] args) {
        Block block1 = new Block(1, null, 0, new ArrayList<>());
        block1.mine();
        block1.showInformation();

        Block block2 = new Block(2, block1.getBlockHash(), 0, new ArrayList<>());
        block2.addTransaction(new Transaction("홍1", "홍2", 1.5));
        block2.addTransaction(new Transaction("홍3", "홍2", 0.7));
        block2.mine();
        block2.showInformation();

        Block block3 = new Block(3, block2.getBlockHash(), 0, new ArrayList<>());
        block3.addTransaction(new Transaction("홍4", "홍5", 8.2));
        block3.addTransaction(new Transaction("홍6", "홍1", 0.4));
        block3.mine();
        block3.showInformation();

        Block block4 = new Block(4, block3.getBlockHash(), 0, new ArrayList<>());
        block4.addTransaction(new Transaction("홍4", "홍6", 0.1));
        block4.mine();
        block4.showInformation();
    }
}
