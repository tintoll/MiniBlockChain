package core;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import util.EC;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.util.ArrayList;

public class BlockChainStarter {


    public static void main(String[] args) throws Exception {
        // 바운시 캐슬의 암호화 라이브러리를 사용하도록 설정
        Security.addProvider(new BouncyCastleProvider());

        Wallet wallet1 = new Wallet();
        wallet1.setFromFile("private1.pem","public1.pem");

        Wallet wallet2 = new Wallet();
        wallet2.setFromFile("private2.pem","public2.pem");

        Wallet wallet3 = new Wallet();
        wallet3.setFromFile("private3.pem","public3.pem");

        Block block1 = new Block(1, null, 0, new ArrayList<>());
        block1.mine();
        block1.showInformation();

        Block block2 = new Block(2, block1.getBlockHash(), 0, new ArrayList<>());

        // 지갑1에서 지갑2로 코인을 전송했다는 의미를 가진 트랜잭션을 생성
        Transaction transaction1 = new Transaction(wallet1, wallet2.getPublicKey(), 1.5, "2018-05-03 23:05:19.5");
        block2.addTransaction(transaction1);

        // 지갑2에서 지갑3으로 코인을 전송했다는 의미를 가진 트랜잭션을 생성
        Transaction transaction2 = new Transaction(wallet2, wallet3.getPublicKey(), 3.7, "2018-05-04 13:05:19.5");
        block2.addTransaction(transaction2);

        block2.mine();
        block2.showInformation();

        Block block3 = new Block(3, block2.getBlockHash(), 0, new ArrayList<>());

        // 지갑1에서 지갑3으로 코인을 전송했다는 의미를 가진 트랜잭션을 생성합니다.
        Transaction transaction3 = new Transaction(wallet1, wallet3.getPublicKey(), 2.3, "2018-05-06 17:09:21.5");
        block3.addTransaction(transaction3);

        // 지갑2에서 지갑3으로 코인을 전송했다는 의미를 가진 트랜잭션을 생성합니다.
        Transaction transaction4 = new Transaction(wallet2, wallet3.getPublicKey(), 1.4, "2018-05-07 02:11:19.5");
        block3.addTransaction(transaction4);

        block3.mine();
        block3.showInformation();

    }
}
