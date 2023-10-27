import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Define a Student class
class Creditcard {
    private String name;
    private long creditNumber;
    private int cvvNumber;

    private String expDate;

    private String transactions;


    public Creditcard(String name, long creditNumber, int cvvNumber, String expDate, String transactions) {
        this.name = name;
        this.creditNumber = creditNumber;
        this.cvvNumber = cvvNumber;
        this.expDate = expDate;
        this.transactions = transactions;
    }

    // Getters for student properties
    public String getName() {
        return name;
    }

    public long getCreditNumber() {
        return creditNumber;
    }

    public int getCvvNumber() {
        return cvvNumber;
    }

    public String getExpDate() {return expDate;}

    public String getTransactions() {return transactions;}
}

// Define a Block class
class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    private String hash;
    private Creditcard creditCard;

    public Block(int index, String previousHash, Creditcard creditCard) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.creditCard = creditCard;
        this.hash = calculateHash();
    }

    // Calculate the hash of the block
    public String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            int nonce = 0;
            String input;

            while (true) {
                input = index + timestamp + previousHash + creditCard.getName() + creditCard.getCreditNumber() + creditCard.getCvvNumber() + creditCard.getExpDate() + creditCard.getTransactions() + nonce;
                byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
                StringBuilder hexString = new StringBuilder();

                for (byte b : hashBytes) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }

                String hash = hexString.toString();

                // Check if the hash starts with "00"
                if (hash.startsWith("155")) {
                    return hash;
                }

                // If not, increment the nonce and try again
                nonce++;
            }
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    // Getters
    public int getIndex() {
        return index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public Creditcard getCreditCard() {
        return creditCard;
    }
}

// Define a Blockchain class
class Blockchain {
    private List<Block> chain;

    // Constructor
    public Blockchain() {
        chain = new ArrayList<Block>();
        // Create the genesis block (the first block in the chain)
        chain.add(new Block(0, "0", new Creditcard("Genesis Block", 111222333444L, 0,""," ")));
    }

    // Add a new block to the blockchain
    public void addCreditCard(Creditcard creditCard) {
        Block previousBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(previousBlock.getIndex() + 1, previousBlock.getHash(), creditCard);
        chain.add(newBlock);
    }

    public void printBlockchain() {
        for (Block block : chain) {
            System.out.println("Block #" + block.getIndex());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Hash: " + block.getHash());
            System.out.println("Credit Card Name: " + block.getCreditCard().getName());
            System.out.println("Credit Card Number : " + block.getCreditCard().getCreditNumber());
            System.out.println("Credit Card CVV: " + block.getCreditCard().getCvvNumber());
            System.out.println("Credit Card Exp: " + block.getCreditCard().getExpDate());
            System.out.println("Credit Card Transactions: " + block.getCreditCard().getTransactions());
            System.out.println();
        }
    }
}

public class SimpleBlockChain {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();
        // Create student objects and add them to the blockchain
        Creditcard creditCard1 = new Creditcard("Jim Jones", 9999222244443333L, 105,"12/26", "Date: 7/7/23 | Huffy Bike | Price: $174.00 | Approved");
        Creditcard creditCard2 = new Creditcard("Jim Jones", 9011021240043141L, 423,"1/27", "Date: 10/7/23 |  Iphone 15 Pro | Price: $1250.68 | Approved");
        Creditcard creditCard3 = new Creditcard("Jim Jones", 9774887700219966L, 987,"2/28", "Date: 7/7/23 | PS5 | Price: $575.98 | Approved");

        blockchain.addCreditCard(creditCard1);
        blockchain.addCreditCard(creditCard2);
        blockchain.addCreditCard(creditCard3);

        // Print the blockchain
        blockchain.printBlockchain();
    }
}
