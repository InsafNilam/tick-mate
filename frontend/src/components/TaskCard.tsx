import type { Task } from "@/types/task";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import TaskDialog from "./TaskDialog";
import { Button } from "./ui/button";
import { Calendar, CheckCircle2, Pencil, Trash2 } from "lucide-react";
import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogHeader, AlertDialogTitle, AlertDialogTrigger } from "./ui/alert-dialog";
import { toast } from "sonner";

const completeTask = async (task: Task): Promise<Task> => {
    const { id } = task; // extract ID
    const payload = { title: task.title, status: 'COMPLETED' }; // only send what needs to change

    const res = await fetch(`${import.meta.env.VITE_API_URL}/api/tasks/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });

    if (!res.ok) throw new Error('Failed to complete');
    return res.json();
};

const deleteTask = async (id: string): Promise<void> => {
    const res = await fetch(`${import.meta.env.VITE_API_URL}/api/tasks/${id}`, { method: 'DELETE' });
    if (!res.ok) throw new Error('Failed to delete');
};

const TaskCard = ({ task }: { task: Task }) => {
    const queryClient = useQueryClient();

    const completeMutation = useMutation({
        mutationFn: () => completeTask(task),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['tasks'] });
            toast.success("Task has been completed!");
        },
        onError: () => {
            toast.error("Failed to complete the task. Please try again.");
        }
    });

    const deleteMutation = useMutation({
        mutationFn: () => deleteTask(task.id),
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['tasks'] });
            toast.success("Task has been deleted!");
        },
        onError: () => {
            toast.error("Failed to delete the task. Please try again.");
        }
    });

    const priorityColors = {
        HIGH: 'bg-red-100 text-red-700',
        MEDIUM: 'bg-yellow-100 text-yellow-700',
        LOW: 'bg-green-100 text-green-700'
    };

    const statusColors = {
        PENDING: 'bg-gray-100 text-gray-700',
        IN_PROGRESS: 'bg-blue-100 text-blue-700',
        COMPLETED: 'bg-green-100 text-green-700'
    };

    return (
        <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-5 hover:shadow-md transition-shadow">
            <div className="flex items-start justify-between gap-3 mb-3">
                <div className="flex-1 min-w-0">
                    <h3 className="font-semibold text-gray-900 text-lg truncate">{task.title}</h3>
                    <p className="text-gray-600 text-sm mt-1 line-clamp-2">{task.description}</p>
                </div>
                <div className="flex items-center gap-2">
                    <TaskDialog
                        task={task}
                        trigger={
                            <Button variant="ghost" size="sm" className="h-8 w-8 p-0 bg-amber-800">
                                <Pencil className="h-4 w-4 text-white" />
                            </Button>
                        }
                    />
                    <AlertDialog>
                        <AlertDialogTrigger asChild>
                            <Button
                                variant="ghost"
                                size="icon"
                                className="h-8 w-8"
                                aria-label="Delete task"
                                disabled={deleteMutation.isPending}
                            >
                                <Trash2 className="h-4 w-4 text-destructive" aria-hidden="true" />
                                <span className="sr-only">Delete</span>
                            </Button>
                        </AlertDialogTrigger>
                        <AlertDialogContent>
                            <AlertDialogHeader>
                                <AlertDialogTitle>Delete this task?</AlertDialogTitle>
                                <AlertDialogDescription>
                                    This action cannot be undone. The task will be permanently removed.
                                </AlertDialogDescription>
                            </AlertDialogHeader>
                            <AlertDialogFooter>
                                <AlertDialogCancel disabled={deleteMutation.isPending}>Cancel</AlertDialogCancel>
                                <AlertDialogAction
                                    className="bg-destructive text-destructive-foreground hover:opacity-90"
                                    onClick={() => deleteMutation.mutate()}
                                    disabled={deleteMutation.isPending}
                                >
                                    {deleteMutation.isPending ? "Deleting..." : "Delete"}
                                </AlertDialogAction>
                            </AlertDialogFooter>
                        </AlertDialogContent>
                    </AlertDialog>
                </div>
            </div>

            <div className="flex items-center gap-2 mb-4">
                <span className={`px-2.5 py-1 rounded-full text-xs font-medium ${priorityColors[task.priority]}`}>
                    {task.priority}
                </span>
                <span className={`px-2.5 py-1 rounded-full text-xs font-medium ${statusColors[task.status]}`}>
                    {task.status.replace('_', ' ')}
                </span>
            </div>

            {task.dueDate && (
                <div className="flex items-center gap-2 text-sm text-gray-500 mb-4">
                    <Calendar className="h-4 w-4" />
                    <span>Due: {new Date(task.dueDate).toLocaleDateString()}</span>
                </div>
            )}

            <AlertDialog>
                <AlertDialogTrigger asChild>
                    <Button
                        disabled={completeMutation.isPending}
                        className="w-full bg-gradient-to-r from-green-500 to-emerald-600 hover:from-green-600 hover:to-emerald-700"
                    >
                        <CheckCircle2 className="h-4 w-4 mr-2" />
                        Mark as Done
                    </Button>
                </AlertDialogTrigger>

                <AlertDialogContent>
                    <AlertDialogHeader>
                        <AlertDialogTitle>Mark this task as completed?</AlertDialogTitle>
                        <AlertDialogDescription>
                            Once completed, this task will be marked as done. You can't able to view it in your tasks list.
                        </AlertDialogDescription>
                    </AlertDialogHeader>

                    <AlertDialogFooter>
                        <AlertDialogCancel disabled={completeMutation.isPending}>Cancel</AlertDialogCancel>
                        <AlertDialogAction
                            className="bg-green-600 text-white hover:opacity-90"
                            onClick={() => completeMutation.mutate()}
                            disabled={completeMutation.isPending}
                        >
                            {completeMutation.isPending ? 'Completing...' : 'Complete Task'}
                        </AlertDialogAction>
                    </AlertDialogFooter>
                </AlertDialogContent>
            </AlertDialog>
        </div>
    );
};

export default TaskCard;