package com.temp.common.base.asm;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;

public class AddFieldAdapter extends ClassAdapter {
    private int accessModifier;
    private String name;
    private String desc;
    private boolean isFieldPresent;

    public AddFieldAdapter(ClassVisitor cv, int accessModifier, String name, String desc) {
        super(cv);
        this.accessModifier = accessModifier;
        this.name = name;
        this.desc = desc;
    }
}