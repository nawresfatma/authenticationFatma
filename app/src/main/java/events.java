public class events {

    private String name ;
    private float price;
    private int imageView[];

    public events(String name, float price, int[] imageView) {
        this.name = name;
        this.price = price;
        this.imageView = imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int[] getImageView() {
        return imageView;
    }

    public void setImageView(int[] imageView) {
        this.imageView = imageView;
    }
}
