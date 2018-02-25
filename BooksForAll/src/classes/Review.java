package classes;

// TODO: Auto-generated Javadoc
/**
 * The Class Review.
 */
public class Review {
	
	/** The approved. */
	private String bookname,nickname,review,approved;
	
	/**
	 * Instantiates a new review.
	 *
	 * @param _bookname the bookname
	 * @param _nickname the nickname
	 * @param _review the review
	 * @param _approved the approved
	 */
	public Review(String _bookname,String _nickname,String _review,String _approved) {
		bookname=_bookname;
		nickname=_nickname;
		review=_review;
		approved=_approved;
	}
	
	/**
	 * Gets the bookname.
	 *
	 * @return the bookname
	 */
	public String getBookname() {
		return bookname;
	}
	
	/**
	 * Gets the nickname.
	 *
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	
	/**
	 * Gets the review.
	 *
	 * @return the review
	 */
	public String getReview() {
		return review;
	}
	
	/**
	 * Gets the approved.
	 *
	 * @return the approved
	 */
	public String getApproved() {
		return approved;
	}

}
