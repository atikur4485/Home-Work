package com.example.livetesting.data_source.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesResponse {
    @SerializedName("categories")
    private List<categories> categoriesList;

    public List<categories> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<categories> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public static class categories {
        private int pc_id, child_count;
        private String name, parent_name, parent_id, image;

        public int getPc_id() {
            return pc_id;
        }

        public void setPc_id(int pc_id) {
            this.pc_id = pc_id;
        }

        public int getChild_count() {
            return child_count;
        }

        public void setChild_count(int child_count) {
            this.child_count = child_count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParent_name() {
            return parent_name;
        }

        public void setParent_name(String parent_name) {
            this.parent_name = parent_name;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @NonNull
        @Override
        public String toString() {
            return "categories{" +
                    "pc_id=" + pc_id +
                    ", child_count=" + child_count +
                    ", name='" + name + '\'' +
                    ", parent_name='" + parent_name + '\'' +
                    ", parent_id='" + parent_id + '\'' +
                    ", image='" + image + '\'' +
                    '}';
        }
    }
}
