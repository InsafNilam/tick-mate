
import { useQuery } from "@tanstack/react-query";
import type { TaskPage } from "@/types/task";
import TaskDialog from "@/components/TaskDialog";
import { Button } from "@/components/ui/button";
import { AlertCircle, ChevronLeft, ChevronRight, Clock, Plus } from "lucide-react";
import { Alert, AlertDescription } from "@/components/ui/alert";
import TaskCard from "@/components/TaskCard";
import { useEffect, useMemo, useState } from "react";
import { toast } from "sonner";

function getApiBase() {
    return import.meta.env.VITE_API_URL
}

async function fetchTasks(page: number, size: number): Promise<TaskPage> {
    const base = getApiBase()
    const params = new URLSearchParams({
        page: String(page),
        size: String(size),
        sort: "dueDate,asc",
        status: "PENDING,IN_PROGRESS",
    })
    const url = `${base ? `${base}` : ""}/api/tasks?${params.toString()}`
    const res = await fetch(url, { headers: { accept: "application/json" } })
    if (!res.ok) throw new Error("Failed to fetch tasks")
    return res.json()
}

const HomePage = () => {
    const [page, setPage] = useState(0)
    const size = 6

    const queryKey = useMemo(() => ["tasks", { page, size, status: "PENDING", base: getApiBase() }], [page, size])

    const { data, isLoading, isFetching, error, refetch } = useQuery({
        queryKey,
        queryFn: () => fetchTasks(page, size),
        placeholderData: (prev) => prev, // keep previous page data to reduce layout shift
        refetchInterval: 30_000,
        refetchOnWindowFocus: false,
        staleTime: 30_000,
        retry: 1,
    })

    const tasks = data?.content ?? []
    const totalPages = Math.max(1, data?.totalPages ?? 1)

    useEffect(() => {
        if (!isLoading && tasks.length === 0 && page > 0) {
            // If current page is empty and not the first page, go back one page
            setPage((p) => Math.max(p - 1, 0));
            toast.info("You have reached the end of the list, going back one page.");
        }
    }, [tasks.length, page, isLoading]);

    return (
        <main className="max-w-7xl mx-auto px-4 py-10">
            {/* Header */}
            <header className="flex items-center justify-between">
                <div>
                    <h1 className="text-3xl font-bold text-primary text-balance">TickMate</h1>
                    <p className="text-sm text-muted-foreground">Manage your tasks efficiently</p>
                </div>
                <TaskDialog
                    trigger={
                        <Button className="bg-primary text-primary-foreground hover:opacity-90">
                            <Plus className="h-4 w-4" />
                            <span className="ml-2">New Task</span>
                        </Button>
                    }
                />
            </header>

            {/* Content area with minimal layout shift */}
            <section className="mt-8">
                {/* Loading state (initial) */}
                {isLoading && !data ? (
                    <div className="min-h-[50vh] grid place-items-center">
                        <div className="flex flex-col items-center">
                            <Clock className="h-10 w-10 animate-spin text-primary mb-3" aria-hidden="true" />
                            <p className="text-muted-foreground">Loading tasks...</p>
                        </div>
                    </div>
                ) : null}

                {/* Error state */}
                {error ? (
                    <div className="min-h-[20vh]">
                        <Alert variant="destructive" className="max-w-xl mx-auto">
                            <AlertCircle className="h-4 w-4" aria-hidden="true" />
                            <AlertDescription className="flex items-center justify-between gap-3">
                                <span>Failed to load tasks. Check your backend connection.</span>
                                <Button size="sm" onClick={() => refetch()} className="bg-primary text-primary-foreground">
                                    Retry
                                </Button>
                            </AlertDescription>
                        </Alert>
                    </div>
                ) : null}

                {/* Skeleton while fetching next page (keeps previous UI visible) */}
                {!isLoading && tasks.length > 0 ? (
                    <>
                        <div className="grid grid-cols-1 md:grid-cols-2 gap-5 min-h-[300px]">
                            {tasks.map((task, idx) => (
                                <TaskCard key={(task.id as string) ?? idx} task={task} />
                            ))}
                            {isFetching
                                ? Array.from({ length: 2 }).map((_, i) => (
                                    <div key={`sk-${i}`} className="rounded-lg border bg-muted animate-pulse h-28" />
                                ))
                                : null}
                        </div>

                        {/* Pagination */}
                        <nav className="mt-6 flex justify-center gap-2" aria-label="Pagination">
                            <Button
                                variant="outline"
                                disabled={page === 0}
                                onClick={() => setPage((p) => Math.max(p - 1, 0))}
                                aria-label="Previous page"
                            >
                                <ChevronLeft className="h-4 w-4" />
                            </Button>

                            {Array.from({ length: totalPages }, (_, i) => (
                                <Button
                                    key={i}
                                    variant={i === page ? "default" : "outline"}
                                    className={i === page ? "bg-primary text-primary-foreground" : ""}
                                    onClick={() => setPage(i)}
                                    aria-current={i === page ? "page" : undefined}
                                    aria-label={`Page ${i + 1}`}
                                >
                                    {i + 1}
                                </Button>
                            ))}

                            <Button
                                variant="outline"
                                disabled={page + 1 >= totalPages}
                                onClick={() => setPage((p) => Math.min(p + 1, totalPages - 1))}
                                aria-label="Next page"
                            >
                                <ChevronRight className="h-4 w-4" />
                            </Button>
                        </nav>
                    </>
                ) : null}

                {/* Empty state */}
                {!isLoading && !error && tasks.length === 0 ? (
                    <div className="min-h-[50vh] grid place-items-center">
                        <div className="text-center bg-card text-card-foreground rounded-2xl border shadow-sm p-10 max-w-xl">
                            <div className="mx-auto mb-4 h-16 w-16 rounded-full bg-muted grid place-items-center">
                                <span className="text-muted-foreground text-lg">0</span>
                            </div>
                            <h3 className="text-lg font-semibold">No tasks yet</h3>
                            <p className="text-muted-foreground mt-1">Create your first task to get started</p>
                            <div className="mt-5">
                                <TaskDialog
                                    trigger={
                                        <Button className="bg-primary text-primary-foreground">
                                            <Plus className="h-4 w-4 mr-2" />
                                            Create Task
                                        </Button>
                                    }
                                />
                            </div>
                        </div>
                    </div>
                ) : null}
            </section>
        </main>
    );

}

export default HomePage;