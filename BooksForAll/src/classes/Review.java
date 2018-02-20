package classes;

public class Review {
	private String bookname,nickname,review,approved;
	public Review(String _bookname,String _nickname,String _review,String _approved) {
		bookname=_bookname;
		nickname=_nickname;
		review=_review;
		approved=_approved;
	}
	public String getBookname() {
		return bookname;
	}
	public String getNickname() {
		return nickname;
	}
	public String getReview() {
		return review;
	}
	public String getApproved() {
		return approved;
	}

}
