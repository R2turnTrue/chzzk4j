package xyz.r2turntrue.chzzk4j.types.channel.live;

import java.util.Objects;

public class ChzzkLiveCategory {
    public static class SearchResponse {
        private ChzzkLiveCategory[] data;

        public ChzzkLiveCategory[] getData() {
            return data;
        }
    }

    public enum Type {
        GAME,
        SPORTS,
        ETC
    }

    private String categoryType;
    private String categoryId;
    private String categoryValue;
    private String posterImageUrl;

    public Type getCategoryType() {
        return Type.valueOf(categoryType);
    }

    public void setCategoryType(Type categoryType) {
        this.categoryType = categoryType.toString();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public String getPosterImageUrl() {
        return posterImageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkLiveCategory that = (ChzzkLiveCategory) o;
        return categoryType == that.categoryType && Objects.equals(categoryId, that.categoryId) && Objects.equals(categoryValue, that.categoryValue) && Objects.equals(posterImageUrl, that.posterImageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryType, categoryId, categoryValue, posterImageUrl);
    }

    @Override
    public String toString() {
        return "ChzzkLiveCategory{" +
                "categoryType=" + categoryType +
                ", categoryId='" + categoryId + '\'' +
                ", categoryValue='" + categoryValue + '\'' +
                ", posterImageUrl='" + posterImageUrl + '\'' +
                '}';
    }
}
