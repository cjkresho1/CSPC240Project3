package networkInterfaces;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Represents the login information for a person. Stores information on the person, 
 * as well as a password and username for the person. Uses "PBKDF2WithHmacSHA512" 
 * hashing, along with a randomly generated salt and iteration count to protect the
 * password.
 * @author Team5
 */
public abstract class LoginAccount 
{
	/**
	 * Person to store the information about the user.
	 */
	protected Person person;
	/**
	 * Username of the user.
	 */
	private String username;
	/**
	 * Hashed password of the user.
	 */
	private byte[] password;
	
	/**
	 * Random salt array.
	 */
	private byte[] salt;
	/**
	 * Length of the salt array.
	 */
	private static final int SALT_LENGTH = 32;
	/**
	 * Random iteration count.
	 */
	private int iterations;
	/**
	 * Key length. 256 is a safe value.
	 */
	private static final int KEY_LENGTH = 256;
	
	protected LoginType type; 
	
	/**
	 * Create a new LoginAccount
	 * @param first first name of user
	 * @param last last name of user
	 * @param email email of user
	 * @param user username of the user
	 * @param pass password of the user
	 */
	public LoginAccount(String first, String last, String email, String user, final String pass)
	{
		//Save information about the user
		person = new Person(first, last, email);
		username = user;
		
		//Set salt value to random
		salt = new byte[SALT_LENGTH];
		new Random().nextBytes((salt));
		
		//Set iteration value to random between [5, 14]
		iterations = 5 + new Random().nextInt(10);
		
		//Save a hash of the password
		password = hashPassword(pass.toCharArray(), salt, iterations, KEY_LENGTH);
	}
	
	/**
	 * Process login information on the user.
	 * @param pass Provided password
	 * @return true if the password matches, false otherwise
	 */
	public boolean login(final String pass)
	{
		boolean val = false;
		
		//Hash the given password
		byte[] hash = hashPassword(pass.toCharArray(), salt, iterations, KEY_LENGTH);
		
		//Set return value to true if passwords match
		if (checkEquals(hash))
		{
			val = true;
		}
		
		//Clear hash of the password for safety
		for (int i = 0; i < hash.length; i++)
		{
			hash[i] = 0;
		}
		
		return val;
	}

	/**
	 * @return user's username
	 */
	public String getUsername()
	{
		return username;
	}
	
	/**
	 * @param user new username
	 */
	public void setUsername(String user)
	{
		username = user;
	}
	
	/**
	 * Resets the password of the LoginAccount.
	 * @param _password the new password
	 */
	public void setPassword(String _password)
	{
		password = hashPassword(_password.toCharArray(), salt, iterations, KEY_LENGTH);
	}
	
	/**
	 * @return the type of user
	 */
	public LoginType getType() 
	{
		return type;
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	/************************PRIVATE METHODS HERE***********************************/

	/**
	 * Hash method for storing and checking the user's password. Uses 
	 * "PBKDF2WithHmacSHA512" hashing.
	 * @param password user's password to be hashed
	 * @param salt salt array for hashing method
	 * @param iterations number of times to run hashing method
	 * @param keyLength key length, 256 is safe value
	 * @return hash of the user's password
	 */
	private static byte[] hashPassword( final char[] password, final byte[] salt, 
			final int iterations, final int keyLength) 
	{ 
		//Hash the user's password
		try 
		{
			SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
			PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
			SecretKey key = skf.generateSecret( spec );
			byte[] res = key.getEncoded( );
			return res; 
		} 
		//Throws exception if the hashing algorithm doesn't exist in the runtime.
		catch( NoSuchAlgorithmException | InvalidKeySpecException e ) 
		{
			throw new RuntimeException( e );
		}
	}
	
	/**
	 * Checks if the given password hash equals the stored password hash
	 * @param hash hash of the given password
	 * @return
	 */
	private boolean checkEquals(byte[] hash) 
	{
		if (hash.length != password.length)
		{
			return false;
		}
		
		for (int i = 0; i < hash.length; i++)
		{
			if (hash[i] != password[i])
			{
				return false;
			}
		}
		
		return true;
	}
}
