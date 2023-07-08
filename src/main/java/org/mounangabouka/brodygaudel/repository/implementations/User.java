/*
 * Copyright (c) 2022-2023 the original author Brody Gaudel MOUNANGA
 * You may use this file and this project under the terms defined by the license
 */

package org.mounangabouka.brodygaudel.repository.implementations;

public class User {
    private String id;
    private String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
