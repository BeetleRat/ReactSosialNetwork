import TotalProgress from "./TotalProgress";
import {connect} from "react-redux";
import {AddNewTask, ChangeCurrentMinutes} from "../../Redux/Redusers/TotalProgressReducer";

let MapStateToProps=(state)=>{
    return{
        tasks: state.progressPage.tasks
    }
}

export default connect(MapStateToProps,{ChangeCurrentMinutes,AddNewTask})(TotalProgress);