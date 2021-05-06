
package com.oxoo.spagreen.models.home_content;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Slide {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image_link")
    @Expose
    private String imageLink;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("action_type")
    @Expose
    private String actionType;
    @SerializedName("action_btn_text")
    @Expose
    private String actionBtnText;
    @SerializedName("action_id")
    @Expose
    private String actionId;
    @SerializedName("action_url")
    @Expose
    private String actionUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionBtnText() {
        return actionBtnText;
    }

    public void setActionBtnText(String actionBtnText) {
        this.actionBtnText = actionBtnText;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

}
