package xyz.r2turntrue.chzzk4j.types.channel.live;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChzzkLiveSettings {
    public static class ModifyRequest {
        private String defaultLiveTitle;
        private String categoryType;
        private String categoryId;
        private String[] tags;

        public ModifyRequest(ChzzkLiveSettings settings) {
            this.defaultLiveTitle = settings.defaultLiveTitle;
            this.categoryId = settings.category.getCategoryId();
            this.categoryType = settings.category.getCategoryType().toString();
            this.tags = settings.tags.toArray(new String[0]);
        }
    }

    private String defaultLiveTitle;
    private ChzzkLiveCategory category;
    private final List<String> tags = new ArrayList<>();

    public String getDefaultLiveTitle() {
        return defaultLiveTitle;
    }

    public void setDefaultLiveTitle(String defaultLiveTitle) {
        this.defaultLiveTitle = defaultLiveTitle;
    }

    public ChzzkLiveCategory getCategory() {
        return category;
    }

    public void setCategory(ChzzkLiveCategory category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkLiveSettings that = (ChzzkLiveSettings) o;
        return Objects.equals(defaultLiveTitle, that.defaultLiveTitle) && Objects.equals(category, that.category) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(defaultLiveTitle, category, tags);
    }

    @Override
    public String toString() {
        return "ChzzkLiveSettings{" +
                "defaultLiveTitle='" + defaultLiveTitle + '\'' +
                ", category=" + category +
                ", tags=" + tags +
                '}';
    }
}
