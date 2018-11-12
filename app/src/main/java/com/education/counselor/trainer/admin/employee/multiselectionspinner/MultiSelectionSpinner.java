package com.education.counselor.trainer.admin.employee.multiselectionspinner;
public class MultiSelectionSpinner {
    private String text;
    private boolean selected = false;
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}