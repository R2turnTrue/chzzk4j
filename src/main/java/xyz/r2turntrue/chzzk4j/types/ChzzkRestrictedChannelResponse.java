package xyz.r2turntrue.chzzk4j.types;

import java.util.Arrays;
import java.util.Objects;

public class ChzzkRestrictedChannelResponse {
    public class Pagination {
        private String next;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pagination that = (Pagination) o;
            return Objects.equals(next, that.next);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(next);
        }

        @Override
        public String toString() {
            return "Pagination{" +
                    "next='" + next + '\'' +
                    '}';
        }
    }

    private ChzzkRestrictedChannel[] data;
    private Pagination page;

    public ChzzkRestrictedChannel[] getRestrictedChannels() {
        return data;
    }

    public String getNextPageToken() {
        return page.next;
    }

    @Override
    public String toString() {
        return "ChzzkRestrictedChannelResponse{" +
                "data=" + Arrays.toString(data) +
                ", page=" + page +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChzzkRestrictedChannelResponse that = (ChzzkRestrictedChannelResponse) o;
        return Objects.deepEquals(data, that.data) && Objects.equals(page, that.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(data), page);
    }
}
