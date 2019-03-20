package core;

import util.Util;

import java.math.BigInteger;
import java.security.Signature;
import java.util.List;

public class Block {
    private static final String ALGORITHM = "SHA1withECDSA";

    private int blockID;
    private String previousBlockHash;
    private int nonce;
    private List<Transaction> transactionList;

    public int getBlockID() {
        return blockID;
    }

    public void setBlockID(int blockID) {
        this.blockID = blockID;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }


    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public Block(int blockID, String previousBlockHash, int nonce, List<Transaction> transactionList) {
        this.blockID = blockID;
        this.previousBlockHash = previousBlockHash;
        this.nonce = nonce;
        this.transactionList = transactionList;
    }
    // 특정한 트랜잭션이 정상적인지 검증합니다.
    private boolean verifyTransaction(Transaction transaction) throws Exception {
        Signature signature;
        signature = Signature.getInstance(ALGORITHM);
        byte[] baText = transaction.getData().getBytes("UTF-8");
        signature.initVerify(transaction.getSender());
        signature.update(baText);
        return signature.verify(new BigInteger(transaction.getSignature(), 16).toByteArray());
    }

    // 정상적인 트랜잭션에 한해서 블록에 추가합니다.
    public void addTransaction(Transaction transaction) throws Exception {
        if(verifyTransaction(transaction)) {
            System.out.println("정상적인 트랜잭션을 발견 ");
            transactionList.add(transaction);
        } else {
            System.out.println("트랜잭션이 바르게 인증되지 않았습니다. ");
        }
    }

    public void showInformation() {
        System.out.println("--------------------");
        System.out.println("블록 번호: " + getBlockID());
        System.out.println("이전 해시: " + getPreviousBlockHash());
        System.out.println("채굴 변수 값: " + getNonce());
        System.out.println("트랜잭션 개수 : " + transactionList.size()+"개");
        for(int i = 0; i < transactionList.size(); i++) {
                System.out.println(transactionList.get(i).getInformation());
        }
        System.out.println("블록 해시: " + getBlockHash());
        System.out.println("--------------------");
    }

    public String getBlockHash() {
        String transactionInformations = "";
        for(int i = 0; i < transactionList.size(); i++) {
            transactionInformations += transactionList.get(i).getInformation();
        }
        return Util.getHash(nonce + transactionInformations + previousBlockHash);
    }

    public void mine() {
        while(true) {
            if(getBlockHash().substring(0,4).equals("0000")) {
                System.out.println(blockID + "번째 블록의 채굴에 성공");
                break;
            }
            nonce++;
        }
    }
}
