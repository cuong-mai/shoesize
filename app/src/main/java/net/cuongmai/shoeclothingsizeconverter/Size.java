package net.cuongmai.shoeclothingsizeconverter;

public class Size {
    private int mId;
    private String mUs;
    private String mEuro;
    private String mUk;
    private String mIn;
    private String mCm;
    private String mBookmarkName;

    public Size(int id, String us, String euro, String uk, String in, String cm, String bookmarkName) {
        mId = id;
        mUs = us;
        mEuro = euro;
        mUk = uk;
        mIn = in;
        mCm = cm;
        mBookmarkName = bookmarkName;
    }

    public boolean isBookmarked() {
        return !mBookmarkName.equals("");
    }

    public int getId() {
        return mId;
    }

    public String getUs() {
        return mUs;
    }

    public String getEuro() {
        return mEuro;
    }

    public String getUk() {
        return mUk;
    }

    public String getIn() {
        return mIn;
    }

    public String getCm() {
        return mCm;
    }

    public String getBookmarkName() {
        return mBookmarkName;
    }

    public void setBookmarkName(String bookmarkName) {
        mBookmarkName = bookmarkName;
    }

}
