import {Company} from "./company.model";

export interface PagedResult<T>{
    content: T[];
    totalPages: number;
}
