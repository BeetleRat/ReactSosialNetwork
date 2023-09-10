import {UserType} from "../../../Types/Types";
import UserReducer, {actions, followUser, unfollowUser, UsersState} from "../UserReducer";
import {CODE, ResponseFromBack} from "../../../ServerAPI/serverInteractionAPI";
import {FollowAPI} from "../../../ServerAPI/followAPI";

jest.mock("../../../ServerAPI/followAPI");
const followAPIMock = FollowAPI as jest.Mocked<typeof FollowAPI>;


let initialisationState: UsersState;
const getStateMock = jest.fn();
const dispatchMock = jest.fn();
const currentUserID = 0;

beforeEach(() => {
        initialisationState = {
            users: [
                {
                    id: 0,
                    name: "Dimich",
                    status: "Ок",
                    imgURL: "",
                    location: {country: "Belarus", city: "Minsk"},
                    followed: false
                },
                {
                    id: 1,
                    name: "Vara",
                    status: "GG",
                    imgURL: "",
                    location: {country: "Uganda", city: "Klinsk"},
                    followed: false
                },
                {
                    id: 2,
                    name: "Misha",
                    status: "WP",
                    imgURL: "",
                    location: {country: "Ucrain", city: "Bobruisck"},
                    followed: true
                },
                {
                    id: 3,
                    name: "Kirill",
                    status: "AAAAAAAA",
                    imgURL: "",
                    location: {country: "USA", city: "NY"},
                    followed: true
                }
            ] as Array<UserType>,
            fetchingUsers: [] as Array<number>,
            pageSize: 5 as number,
            totalUsersCount: 0 as number,
            currentPage: 1 as number,
            isLoading: false as boolean,
            filter: {
                term: "",
                friend: null
            }
        };
        // Очищаем пустышки
        getStateMock.mockClear();
        dispatchMock.mockClear();
        // Очищаем используемые функции сервисов пустышек
        followAPIMock.unfollowUser.mockClear();
        followAPIMock.followUser.mockClear();
    }
)

describe(
    'User reducer',
    () => {
        test(
            'commit follow text',
            () => {
                // 1. Задаем стартовые данные
                const unfollowedUserID = 1;
                const stateUnfollowedUserID = 0;
                const action = actions.commitFollow(unfollowedUserID);

                // 2. Выполняем тестируемое действие над стартовыми данными
                const newState = UserReducer(initialisationState, action);

                // 3. Проверяем соответствует ли результат ожидаемому
                expect(newState.users[stateUnfollowedUserID].followed).toBeFalsy();
                expect(newState.users[unfollowedUserID].followed).toBeTruthy();
            }
        );
        test(
            'commit unfollow text',
            () => {
                // 1. Задаем стартовые данные
                const followedUserID = 2;
                const stateFollowedUserID = 3;
                const action = actions.commitUnfollow(followedUserID);

                // 2. Выполняем тестируемое действие над стартовыми данными
                const newState = UserReducer(initialisationState, action);

                // 3. Проверяем соответствует ли результат ожидаемому
                expect(newState.users[stateFollowedUserID].followed).toBeTruthy();
                expect(newState.users[followedUserID].followed).toBeFalsy();
            }
        );
        test(
            'follow thunk test',
            async () => {
                // 1. Задаем стартовые данные

                // Задаем данные возвращаемые пустышкой
                const result: ResponseFromBack = {
                    resultCode: CODE.AUTHORIZED_AND_COMPLETED,
                    message: ""
                }
                // Метод сервиса будет вызываться асинхронно,
                // потому возвращаемые данные оборачиваются в Promise
                followAPIMock.followUser.mockReturnValue(Promise.resolve(result));

                // Создаем thunk
                const userID = 2;
                const thunk = followUser(currentUserID, userID);


                // 2. Выполняем тестируемое действие над стартовыми данными
                await thunk(dispatchMock, getStateMock, {});


                // 3. Проверяем соответствует ли результат ожидаемому

                // dispatchMock был вызван 3 раза
                expect(dispatchMock).toBeCalledTimes(3);
                // При первом вызове dispatchMock
                // в него передали actionCreator: actions.toggleFetchingUser(true, userID)
                expect(dispatchMock).toHaveBeenNthCalledWith(1, actions.toggleFetchingUser(true, userID));
                // При втором вызове dispatchMock
                // в него передали actionCreator: actions.commitFollow(userID)
                expect(dispatchMock).toHaveBeenNthCalledWith(2, actions.commitFollow(userID));
                // При третьем вызове dispatchMock
                // в него передали actionCreator: actions.toggleFetchingUser(false, userID)
                expect(dispatchMock).toHaveBeenNthCalledWith(3, actions.toggleFetchingUser(false, userID));
            }
        );
        test(
            'unfollow thunk test',
            async () => {
                // 1. Задаем стартовые данные
                const result: ResponseFromBack = {
                    resultCode: CODE.AUTHORIZED_AND_COMPLETED,
                    message: ""
                }
                followAPIMock.unfollowUser.mockReturnValue(Promise.resolve(result));

                const userID = 3;
                const thunk = unfollowUser(currentUserID, userID);

                // 2. Выполняем тестируемое действие над стартовыми данными
                await thunk(dispatchMock, getStateMock, {});

                // 3. Проверяем соответствует ли результат ожидаемому
                expect(dispatchMock).toBeCalledTimes(3);
                expect(dispatchMock).toHaveBeenNthCalledWith(1, actions.toggleFetchingUser(true, userID));
                expect(dispatchMock).toHaveBeenNthCalledWith(2, actions.commitUnfollow(userID));
                expect(dispatchMock).toHaveBeenNthCalledWith(3, actions.toggleFetchingUser(false, userID));
            }
        );
    }
);