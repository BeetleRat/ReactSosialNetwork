import {create} from "react-test-renderer";
import ProfileStatusClassComponent from "./ProfileStatusClassComponent";

describe(
    'Profile status component',
    () => {
        test('status from props should be in state',
            () => {
                // 1. Задаем стартовые данные
                let props = {
                    status: "Тестируемый статус",
                    authUserID: 23
                };

                // 2. Создаем тестируемый компонент
                const component = create(<ProfileStatusClassComponent {...props}/>);
                const instance = component.getInstance();

                // 3. Проверяем соответствует ли созданный компонент ожидаемому
                expect(instance.state.status).toBe(props.status);
            }
        );

        test('by default profile should not contain input component',
            async () => {
                // 1. Задаем стартовые данные
                let props = {
                    status: "Тестируемый статус",
                    authUserID: 23
                };

                // 2. Создаем тестируемый компонент
                const component = create(<ProfileStatusClassComponent {...props}/>);
                const root = component.root;
                let pComponent = await root.findByType("p");

                // 3. Проверяем соответствует ли созданный компонент ожидаемому
                expect(pComponent).not.toBeNull();
            }
        );
        test('by default profile should contain p component with status',
            async () => {
                // 1. Задаем стартовые данные
                let props = {
                    status: "Тестируемый статус",
                    authUserID: 23
                };

                // 2. Создаем тестируемый компонент
                const component = create(<ProfileStatusClassComponent {...props}/>);
                const root = component.root;
                let pComponent = await root.findByType("p");

                // 3. Проверяем соответствует ли созданный компонент ожидаемому
                expect(pComponent.children).toContain(props.status);
            }
        );
        test('input should be displayed in edit mode',
            async () => {
                // 1. Задаем стартовые данные
                let props = {
                    status: "Тестируемый статус",
                    authUserID: 23
                };

                // 2. Создаем тестируемый компонент
                const component = create(<ProfileStatusClassComponent {...props}/>);
                const root = component.root;

                let pComponent = await root.findByType("p");
                // Вызываем метод найденной компоненты
                pComponent.props.onDoubleClick();

                let inputComponent = await root.findByType("input");

                // 3. Проверяем соответствует ли созданный компонент ожидаемому
                expect(inputComponent).not.toBeNull();
            }
        );
        test('updateStatus must be called with the correct parameters',
            async () => {
                // 1. Задаем стартовые данные
                let mockCallback = jest.fn();
                let props = {
                    status: "Тестируемый статус",
                    authUserID: 23,
                    updateStatus: mockCallback
                };

                // 2. Создаем тестируемый компонент
                const component = create(<ProfileStatusClassComponent {...props}/>);
                const root = component.root;

                let pComponent = await root.findByType("p");
                // Вызываем метод найденной компоненты
                pComponent.props.onDoubleClick();

                let inputComponent = await root.findByType("input");
                inputComponent.props.onBlur();

                // 3. Проверяем соответствует ли созданный компонент ожидаемому
                // Шпионская функция должна быть вызвана 1 раз
                expect(mockCallback.mock.calls.length).toBe(1);
                // При первом вызове в нее должно быть переданно 2 входных параметра
                // первый входной параметр должен быть равен props.authUserID
                expect(mockCallback.mock.calls[0][0]).toBe(props.authUserID);
                // второй входной параметр должен быть равен props.status
                expect(mockCallback.mock.calls[0][1]).toBe(props.status);
            }
        );
    }
);