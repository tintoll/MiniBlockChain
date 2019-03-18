import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class Test {
    public static void main(String[] args) throws Exception {
        // 무작위의 개인키와 공개키를 생성하기 위해 키 생성 객체를 정의
        KeyPairGenerator kpg;
        kpg = KeyPairGenerator.getInstance("EC","SunEC");

        // 타원 곡선 디지털 서명 알고리즘 객체를 생성
        ECGenParameterSpec ecps;
        // 세부 알고리즘 스펙을 정의
        ecps = new ECGenParameterSpec("sect163k1");
        // 랜덤으로 임의의 키를 생성
        kpg.initialize(ecps, new SecureRandom());

        // 개인키와 공개키 한 쌍을 생성
        KeyPair kp = kpg.genKeyPair();
        PrivateKey privKey = kp.getPrivate();
        PublicKey pubKey = kp.getPublic();

        // 서명 객체를 생성해 개인키를 설정
        Signature ecdsa;
        ecdsa = Signature.getInstance("SHA1withECDSA", "SunEC");
        ecdsa.initSign(privKey);

        // 임의의 원래 문장을 정의
        String text = "동이가 율이에게 100 코인 전송";
        System.out.println("원래 문장 : "+text);

        // 뭔래 문장에 대해 암호화를 수행해 서명 값(암호문)을 얻는다.
        ecdsa.update(text.getBytes("UTF-8"));
        byte[] signatureByte = ecdsa.sign();
        System.out.println("암호문 : 0x"+(new BigInteger(1, signatureByte).toString(16).toUpperCase()));

        // 서명 객체를 생성해 공개키를 이용하여 복호화 할 수 있도록 설정
        Signature signature;
        signature = Signature.getInstance("SHA1withECDSA","SunEC");
        signature.initVerify(pubKey);

        // 원래 문장을 공개키로 복호화
        signature.update(text.getBytes("UTF-8"));
        System.out.println("원래 문장 검증 :"+signature.verify(signatureByte));

        // 변경된 문장을 공개키로 검증
        signature.update("변경된 문장".getBytes("UTF-8"));
        System.out.println("변경된 문장 검증 :"+signature.verify(signatureByte));

    }
}
