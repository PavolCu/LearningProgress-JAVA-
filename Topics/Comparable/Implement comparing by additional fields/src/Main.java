class Article implements Comparable<Article> {
    private String title;
    private int size;

    public Article(String title, int size) {
        this.title = title;
        this.size = size;
    }

    public String getTitle() {
        return this.title;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public int compareTo(Article otherArticle) {
        int compare = Integer.compare(this.getSize(), otherArticle.getSize());
        if (compare == 0) {
            compare = this.getTitle().compareTo(otherArticle.getTitle());
        }
        return compare;
    }
}