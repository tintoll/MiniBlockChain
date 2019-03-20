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

        // 파일 형태로 저장한 키 데이터를 프로그램으로 불러옵니다.
        PrivateKey privateKey1 = ec.readPrivateKeyFromPemFile("private1.pem");
        PublicKey publicKey1 = ec.readPublicKeyFromPemFile("public1.pem");
        PrivateKey privateKey2 = ec.readPrivateKeyFromPemFile("private2.pem");
        PublicKey publicKey2 = ec.readPublicKeyFromPemFile("public2.pem");

        Signature ecdsa;
        ecdsa = Signature.getInstance("SHA1withECDSA");
        // 개인키 1을 이용해 암호화(서명)합니다.
        ecdsa.initSign(privateKey1);
        String text = "평문입니다.";
        System.out.println("평문 정보: " + text);
        byte[] baText = text.getBytes("UTF-8");
        // 평문 데이터를 암호화하여 서명한 데이터를 출력합니다.
        ecdsa.update(baText);
        byte[] baSignature = ecdsa.sign();
        System.out.println("서명된 값: 0x" + (new BigInteger(1, baSignature).toString(16)).toUpperCase());



        Signature signature;
        signature = Signature.getInstance("SHA1withECDSA");
        // 검증할 때는 공개키 2를 이용해 복호화를 수행합니다.
        signature.initVerify(publicKey2);
        signature.update(baText);
        boolean result = signature.verify(baSignature);

        // 개인키와 매칭되는 공개키가 아니므로 복호화에 실패합니다.
        System.out.println("신원 검증: " + result);

    }
}
