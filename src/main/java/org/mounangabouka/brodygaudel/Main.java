/*
 * Copyright (c) 2022-2023 the original author Brody Gaudel MOUNANGA
 * You may use this file and this project under the terms defined by the license
 */

package org.mounangabouka.brodygaudel;

import org.mounangabouka.brodygaudel.repository.Repository;
import org.mounangabouka.brodygaudel.repository.implementations.TxtFileRepository;
import org.mounangabouka.brodygaudel.repository.implementations.User;

public class Main {
    public static void main(String[] args) {
        Repository<User> userRepository = new TxtFileRepository<>("user.txt", User.class);
        User user = userRepository.save(new User("2250", "MOUNANGA BOUKA"));
        System.out.println(user);
    }
}