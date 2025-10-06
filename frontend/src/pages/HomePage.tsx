
import axios from "axios";
import { useQuery } from "@tanstack/react-query";
import type { TaskPage } from "@/types/task";


const fetchTasks = async (): Promise<TaskPage[]> => {
    const res = await axios.get<TaskPage[]>(`${import.meta.env.VITE_API_URL}/api/tasks`);
    return res.data;
};

const HomePage = () => {
    return (
        <div className="mt-4 flex flex-col gap-4">
            Hello
        </div>
    );
}

export default HomePage;