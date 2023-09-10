import styleClass from "./PageNumbers.module.css"
import PageRow from "./PageRow";
import Space from "../Space/Space";
import React from "react";

type PropsType = {
    changePage: (pageNumber: number) => void,
    currentPage: number,
    pageSize: number,
    totalItems: number
}

const PageNumbers: React.FC<PropsType> = (props) => {

    const maxShowedPages = 5;
    const spaceCount = 3;
    const halfShowedPages = Math.floor(maxShowedPages / 2);
    let maxPage = Math.ceil(props.totalItems / props.pageSize);
    let firstPage = 1;
    let pages = [];

    if (maxPage <= maxShowedPages) {
        for (let i = 1; i <= maxPage; i++) {
            pages.push(i);
        }

        return (
            <div>
                <PageRow currentPage={props.currentPage} pages={pages} changePage={props.changePage}/>
            </div>
        );
    }

    pages.push(props.currentPage);
    for (let i = 1; i <= halfShowedPages; i++) {
        let positivePush =
            props.currentPage + i > maxPage
                ? props.currentPage - (halfShowedPages) - (i + 1 - maxPage + props.currentPage)
                : props.currentPage + i;

        let negativePush =
            props.currentPage - i < firstPage
                ? props.currentPage + (halfShowedPages) + (i + firstPage - props.currentPage)
                : props.currentPage - i;

        if (negativePush > positivePush) {
            if (negativePush > props.currentPage) {
                pages = [...pages, positivePush, negativePush];
            } else {
                pages = [positivePush, negativePush, ...pages];
            }
        } else {
            pages = [negativePush, ...pages, positivePush];
        }
    }

    if (pages.includes(firstPage)) {
        pages.shift();

        return (
            <div>
                <PageRow changePage={props.changePage}
                         currentPage={props.currentPage}
                         pages={[firstPage]}/>
                <Space count={spaceCount}/>
                <PageRow changePage={props.changePage}
                         currentPage={props.currentPage}
                         pages={pages}/>
                <span>...</span>
                <PageRow changePage={props.changePage}
                         currentPage={props.currentPage}
                         pages={[maxPage]}/>
            </div>
        );
    }

    if (pages.includes(maxPage)) {
        pages.pop();

        return (
            <div>
                <PageRow changePage={props.changePage}
                         currentPage={props.currentPage}
                         pages={[firstPage]}/>
                <span>...</span>
                <PageRow changePage={props.changePage}
                         currentPage={props.currentPage}
                         pages={pages}/>
                <Space count={spaceCount}/>
                <PageRow changePage={props.changePage}
                         currentPage={props.currentPage}
                         pages={[maxPage]}/>
            </div>
        );
    }

    return (
        <div>
            <PageRow changePage={props.changePage}
                     currentPage={props.currentPage}
                     pages={[firstPage]}/>
            <span>...</span>
            <PageRow changePage={props.changePage}
                     currentPage={props.currentPage}
                     pages={pages}/>
            <span>...</span>
            <PageRow changePage={props.changePage}
                     currentPage={props.currentPage}
                     pages={[maxPage]}/>
        </div>
    );
}

export default PageNumbers;
