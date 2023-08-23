import styleClass from "./PageNumbers.module.css"
import classNames from "classnames"

const PageRow = ({currentPage, pages, changePage}) => {
    return (
        <span>
            {
                pages.map((page) =>
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