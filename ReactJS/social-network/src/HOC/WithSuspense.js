import React from "react";

const ComponentRenderedAtLoadTime = (props) => {
    return (
        <div>
            Loading...
        </div>
    );
}

export const withSuspense = (Component) => {
    return (props) => {
        return (
            <React.Suspense fallback={<ComponentRenderedAtLoadTime/>}>
                <Component {...props}/>
            </React.Suspense>
        );
    }
}

