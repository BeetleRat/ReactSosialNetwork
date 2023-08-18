import ProfileReducer, {addPost, deletePost} from "../ProfileReducer";
import React from "react";

let initialState = {
    user: null,
    postsArray: [
        {id: '1', text: "Хапнул пива", likes: '25'},
        {id: '2', text: "Лифт", likes: '1'},
        {id: '3', text: "Бурятки", likes: '123'},
        {id: '4', text: "Еще один пост", likes: '2'},
        {id: '5', text: "Лай", likes: '5'}
    ]
};

describe('Profile reducer',
    () => {
        test('post array length should be incremented',
            () => {
                // 1. Задаем стартовые данные
                let action = addPost("Текст нового поста");
                let expectedLength = initialState.postsArray.length + 1;

                // 2. Выполняем тестируемое действие над стартовыми данными
                let newState = ProfileReducer(initialState, action);

                // 3. Проверяем соответствует ли результат ожидаемому
                expect(newState.postsArray.length).toBe(expectedLength);
            }
        );

        test('new post message should be correct',
            () => {
                // 1. Задаем стартовые данные
                let action = addPost("Текст нового поста");
                let expectedMessage = "Текст нового поста";

                // 2. Выполняем тестируемое действие над стартовыми данными
                let newState = ProfileReducer(initialState, action);

                // 3. Проверяем соответствует ли результат ожидаемому
                expect(newState.postsArray[newState.postsArray.length - 1].text)
                    .toBe(expectedMessage);
            }
        );

        test('after delete post array length should be decremented',
            () => {
                // 1. Задаем стартовые данные
                let action = deletePost(1);
                let expectedLength = initialState.postsArray.length - 1;

                // 2. Выполняем тестируемое действие над стартовыми данными
                let newState = ProfileReducer(initialState, action);

                // 3. Проверяем соответствует ли результат ожидаемому
                expect(newState.postsArray.length)
                    .toBe(expectedLength);
            }
        );

        test('after delete wrong id post should not be changed',
            () => {
                // 1. Задаем стартовые данные
                let action = deletePost(19999);
                let expectedLength = initialState.postsArray.length;

                // 2. Выполняем тестируемое действие над стартовыми данными
                let newState = ProfileReducer(initialState, action);

                // 3. Проверяем соответствует ли результат ожидаемому
                expect(newState.postsArray.length)
                    .toBe(expectedLength);
            }
        );
    }
);




