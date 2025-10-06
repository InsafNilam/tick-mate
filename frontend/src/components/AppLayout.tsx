import { Outlet } from "react-router-dom";

const AppLayout = () => {
    return (
        <div className="px-4 md:px-8 lg:px-16 bg-gradient-to-br from-blue-50 via-white to-purple-50">
            <Outlet />
        </div>

    );
};

export default AppLayout;