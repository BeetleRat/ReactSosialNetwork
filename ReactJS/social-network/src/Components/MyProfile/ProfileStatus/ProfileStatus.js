import React from "react";

class ProfileStatus extends React.Component {
    state = {
        editMode: false
    }

    // componentDidUpdate(prevProps, prevState, snapshot) {
    //     if(this.state!==prevState){
    //         console.log(`PrevState: ${prevState}; newState: ${this.state}`);
    //         console.log(`prevProps: ${prevProps}; newProps: ${this.props}`);
    //     }
    // }

    editModeON() {
        this.setState(
            {
                editMode: true
            }
        );
    }

    editModeOFF(event) {
        let status = event.target.value;
        this.setState(
            {
                editMode: false
            }
        );

        this.props.updateStatus(status);
    }

    changeInputTextStatus(event) {
        let status = event.target.value;

        return this.props.setStatus(status);
    }

    render() {
        return (
            <div>
                {
                    this.state.editMode
                        ? <div>
                            <label htmlFor="statusInput">Статус: </label>
                            <input id="statusInput" name="statusInput" type="text" value={this.props.status}
                                   onChange={this.changeInputTextStatus.bind(this)} onBlur={this.editModeOFF.bind(this)}
                                   autoFocus={true}/>
                        </div>
                        : <div>
                            <p name="status"
                               onDoubleClick={this.editModeON.bind(this)}>Статус: {this.props.status ? this.props.status : "No status."}</p>
                        </div>
                }

            </div>
        );
    }
}

export default ProfileStatus