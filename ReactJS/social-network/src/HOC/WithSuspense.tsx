import React from "react";

const ComponentRenderedAtLoadTime = () => {
    return (
        <div>
            Loading...
        </div>
    );
}

export function withSuspense<T extends object>(WrappedComponent:  React.ComponentType<T> ) {
    return (props: T) => {
        return (
            <React.Suspense fallback={<ComponentRenderedAtLoadTime/>}>
                <WrappedComponent {...props as T}/>
            </React.Suspense>
        );
    }
}

