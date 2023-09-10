import styleClass from "./PageNumbers.module.css"
import classNames from 'classnames'
import React from "react";

type PropsType = {
    currentPage: number,
    pages: Array<number>,
    changePage: (pageNumber: number) => void
}

const PageRow: React.FC<PropsType> = ({currentPage, pages, changePage}) => {
    return (
        <span>
            {
                pages.map((page: number) =>
                    <span key={page}
                          onClick={() => changePage(page)}
                        className={classNames(styleClass.page, {[styleClass.selectedPage]: currentPage === page})}
                    >
                        {page}
                    </span>)
            }
        </span>

    );
}

export default PageRow;