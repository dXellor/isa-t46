export interface PagedResult<T>{
    results: T[];
    totalPages: number;
}