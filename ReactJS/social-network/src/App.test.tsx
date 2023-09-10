import React from "react";
import ReactDOM from "react-dom";
import App from "./App";

test('renders without crashing',
    () => {
        // Создаем объект div в Virtual DOM
        const div = document.createElement('div');
        // Пытаемся отрендерить внутри объекта div, компоненту App
        ReactDOM.render(<App/>, div);
        // Демонтируем объект div, очищаем место за собой
        ReactDOM.unmountComponentAtNode(div);
    }
);