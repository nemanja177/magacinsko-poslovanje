import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { combineLatest, filter, Observable, switchMap, tap } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnalitickaMagacinskaKartica } from '../analiticka-magacinska-kartica.model';
import { ASC, DESC, SORT, ITEM_DELETED_EVENT, DEFAULT_SORT_DATA } from 'app/config/navigation.constants';
import { EntityArrayResponseType, AnalitickaMagacinskaKarticaService } from '../service/analiticka-magacinska-kartica.service';
import { AnalitickaMagacinskaKarticaDeleteDialogComponent } from '../delete/analiticka-magacinska-kartica-delete-dialog.component';
import { SortService } from 'app/shared/sort/sort.service';

@Component({
  selector: 'jhi-analiticka-magacinska-kartica',
  templateUrl: './analiticka-magacinska-kartica.component.html',
})
export class AnalitickaMagacinskaKarticaComponent implements OnInit {
  analitickaMagacinskaKarticas?: IAnalitickaMagacinskaKartica[];
  isLoading = false;

  predicate = 'id';
  ascending = true;

  constructor(
    protected analitickaMagacinskaKarticaService: AnalitickaMagacinskaKarticaService,
    protected activatedRoute: ActivatedRoute,
    public router: Router,
    protected sortService: SortService,
    protected modalService: NgbModal
  ) {}

  trackId = (_index: number, item: IAnalitickaMagacinskaKartica): number =>
    this.analitickaMagacinskaKarticaService.getAnalitickaMagacinskaKarticaIdentifier(item);

  ngOnInit(): void {
    this.load();
  }

  delete(analitickaMagacinskaKartica: IAnalitickaMagacinskaKartica): void {
    const modalRef = this.modalService.open(AnalitickaMagacinskaKarticaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.analitickaMagacinskaKartica = analitickaMagacinskaKartica;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed
      .pipe(
        filter(reason => reason === ITEM_DELETED_EVENT),
        switchMap(() => this.loadFromBackendWithRouteInformations())
      )
      .subscribe({
        next: (res: EntityArrayResponseType) => {
          this.onResponseSuccess(res);
        },
      });
  }

  load(): void {
    this.loadFromBackendWithRouteInformations().subscribe({
      next: (res: EntityArrayResponseType) => {
        this.onResponseSuccess(res);
      },
    });
  }

  navigateToWithComponentValues(): void {
    this.handleNavigation(this.predicate, this.ascending);
  }

  protected loadFromBackendWithRouteInformations(): Observable<EntityArrayResponseType> {
    return combineLatest([this.activatedRoute.queryParamMap, this.activatedRoute.data]).pipe(
      tap(([params, data]) => this.fillComponentAttributeFromRoute(params, data)),
      switchMap(() => this.queryBackend(this.predicate, this.ascending))
    );
  }

  protected fillComponentAttributeFromRoute(params: ParamMap, data: Data): void {
    const sort = (params.get(SORT) ?? data[DEFAULT_SORT_DATA]).split(',');
    this.predicate = sort[0];
    this.ascending = sort[1] === ASC;
  }

  protected onResponseSuccess(response: EntityArrayResponseType): void {
    const dataFromBody = this.fillComponentAttributesFromResponseBody(response.body);
    this.analitickaMagacinskaKarticas = this.refineData(dataFromBody);
  }

  protected refineData(data: IAnalitickaMagacinskaKartica[]): IAnalitickaMagacinskaKartica[] {
    return data.sort(this.sortService.startSort(this.predicate, this.ascending ? 1 : -1));
  }

  protected fillComponentAttributesFromResponseBody(data: IAnalitickaMagacinskaKartica[] | null): IAnalitickaMagacinskaKartica[] {
    return data ?? [];
  }

  protected queryBackend(predicate?: string, ascending?: boolean): Observable<EntityArrayResponseType> {
    this.isLoading = true;
    const queryObject = {
      sort: this.getSortQueryParam(predicate, ascending),
    };
    return this.analitickaMagacinskaKarticaService.query(queryObject).pipe(tap(() => (this.isLoading = false)));
  }

  protected handleNavigation(predicate?: string, ascending?: boolean): void {
    const queryParamsObj = {
      sort: this.getSortQueryParam(predicate, ascending),
    };

    this.router.navigate(['./'], {
      relativeTo: this.activatedRoute,
      queryParams: queryParamsObj,
    });
  }

  protected getSortQueryParam(predicate = this.predicate, ascending = this.ascending): string[] {
    const ascendingQueryParam = ascending ? ASC : DESC;
    if (predicate === '') {
      return [];
    } else {
      return [predicate + ',' + ascendingQueryParam];
    }
  }
}
