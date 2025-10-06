import { Outlet } from "react-router-dom";

const AppLayout = () => {
    return (
        <div className="px-4 md:px-8 lg:px-16">
            <Outlet />
        </div>
    );
};

export default AppLayout;