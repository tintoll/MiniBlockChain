package core;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import util.EC;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;

public class BlockChainStarter {


    public static void main(String[] args) throws Exception {
        // 바운시 캐슬의 암호화 라이브러리를 사용하도록 설정
        Security.addProvider(new BouncyCastleProvider());

        // 타원 곡선 객체를 생성해 개인키와 공개키를 저장합니다.
        EC ec = new EC();
        // 총 두 쌍의 키를 생성해 파일 형태로 저장합니다.
        ec.generate("private1.pem", "public1.pem");
        ec.generate("private2.pem", "public2.pem");
        ec.generate("private3.pem", "public3.pem");


    }
}
