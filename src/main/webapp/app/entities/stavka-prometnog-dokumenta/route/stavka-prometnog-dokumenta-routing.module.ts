import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { StavkaPrometnogDokumentaComponent } from '../list/stavka-prometnog-dokumenta.component';
import { StavkaPrometnogDokumentaDetailComponent } from '../detail/stavka-prometnog-dokumenta-detail.component';
import { StavkaPrometnogDokumentaUpdateComponent } from '../update/stavka-prometnog-dokumenta-update.component';
import { StavkaPrometnogDokumentaRoutingResolveService } from './stavka-prometnog-dokumenta-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const stavkaPrometnogDokumentaRoute: Routes = [
  {
    path: '',
    component: StavkaPrometnogDokumentaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: StavkaPrometnogDokumentaDetailComponent,
    resolve: {
      stavkaPrometnogDokumenta: StavkaPrometnogDokumentaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: StavkaPrometnogDokumentaUpdateComponent,
    resolve: {
      stavkaPrometnogDokumenta: StavkaPrometnogDokumentaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: StavkaPrometnogDokumentaUpdateComponent,
    resolve: {
      stavkaPrometnogDokumenta: StavkaPrometnogDokumentaRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(stavkaPrometnogDokumentaRoute)],
  exports: [RouterModule],
})
export class StavkaPrometnogDokumentaRoutingModule {}
