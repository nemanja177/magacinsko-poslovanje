import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PrometniDokumentComponent } from '../list/prometni-dokument.component';
import { PrometniDokumentDetailComponent } from '../detail/prometni-dokument-detail.component';
import { PrometniDokumentUpdateComponent } from '../update/prometni-dokument-update.component';
import { PrometniDokumentRoutingResolveService } from './prometni-dokument-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const prometniDokumentRoute: Routes = [
  {
    path: '',
    component: PrometniDokumentComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PrometniDokumentDetailComponent,
    resolve: {
      prometniDokument: PrometniDokumentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrometniDokumentUpdateComponent,
    resolve: {
      prometniDokument: PrometniDokumentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PrometniDokumentUpdateComponent,
    resolve: {
      prometniDokument: PrometniDokumentRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(prometniDokumentRoute)],
  exports: [RouterModule],
})
export class PrometniDokumentRoutingModule {}
