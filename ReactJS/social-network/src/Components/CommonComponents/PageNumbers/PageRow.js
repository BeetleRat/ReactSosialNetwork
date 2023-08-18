import styleClass from "./PageNumbers.module.css"

const PageRow = (props) => {
    return (
        <span>
            {
                props.pages.map((page) =>
                    <span key={page}
                          onClick={() => props.changePage(page)}
                          className={
                              props.currentPage === page
                                  ? styleClass.selectedPage
                                  : styleClass.page}
                    >
                        {page}
                    </span>)
            }
        </span>

    );
}
export default PageRow;