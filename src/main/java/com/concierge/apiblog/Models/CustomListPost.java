package com.concierge.apiblog.Models;

import java.util.List;
import java.util.Objects;

public class CustomListPost {
    List<Post> posts;
    long total;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomListPost listPost = (CustomListPost) o;
        return total == listPost.total && Objects.equals(posts, listPost.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(posts, total);
    }

    @Override
    public String toString() {
        return "ListPost{" +
                "posts=" + posts +
                ", total=" + total +
                '}';
    }
}
