package org.fri.entice.webapp.entry;

public class RecipeBuild extends MyEntry {
    private String recipeId;
    private String status;
    private String message;

    public RecipeBuild(String id) {
        super(id);
    }

    public RecipeBuild(String id, String recipeId, String status, String message) {
        super(id);
        this.recipeId = recipeId;
        this.status = status;
        this.message = message;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
